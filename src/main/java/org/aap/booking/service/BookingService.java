package org.aap.booking.service;

import org.aap.booking.dto.BookingRequest;
import org.aap.booking.dto.BookingResponse;
import org.aap.booking.dto.BookingStatus;
import org.aap.booking.entity.Show;
import org.aap.booking.exception.BusinessLogicException;
import org.aap.booking.exception.ResourceNotFoundException;
import org.aap.booking.entity.Booking;
import org.aap.booking.entity.BookingSeat;
import org.aap.booking.entity.Payment;
import org.aap.booking.repository.BookingRepository;
import org.aap.booking.repository.BookingSeatRepository;
import org.aap.booking.repository.ShowRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.aap.booking.dto.PaymentStatus.SUCCESS;

@Service
public class BookingService {

    private final ShowRepository showRepository;

    private final BookingRepository bookingRepository;

    private final BookingSeatRepository bookingSeatRepository;

    private final PaymentService paymentService;

    public BookingService(ShowRepository showRepository, BookingRepository bookingRepository, BookingSeatRepository bookingSeatRepository, PaymentService paymentService) {
        this.showRepository = showRepository;
        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.paymentService = paymentService;
    }

    @Transactional
    public BookingResponse bookTickets(BookingRequest request) {
        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() -> new ResourceNotFoundException("SHOW_NOT_FOUND", "Show not found with ID: " + request.getShowId()));

        if (request.getSeatNumbers() == null || request.getSeatNumbers().isEmpty()) {
            throw new BusinessLogicException ("INVALID_BOOKING_REQUEST", "At least one seat must be selected");
        }

        // Calculate amount = number of seats * ticketPrice
        BigDecimal amount = show.getTicketPrice().multiply(new BigDecimal(request.getSeatNumbers().size()));

        // Create booking PENDING_PAYMENT
        Booking booking = new Booking(show, amount);
        booking.setStatus(BookingStatus.PENDING_PAYMENT);
        booking = bookingRepository.save(booking);

        // Reserve seats
        List<BookingSeat> seats = reserveSeats(booking, show, request.getSeatNumbers());

        // Update available seats (existing logic)
        updateAvailableSeats(show, request.getSeatNumbers().size());

        // todo
        Payment payment = paymentService.initiatePayment(booking);

        if (SUCCESS == payment.getStatus()) {
            booking.setStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking);
        } else {
            // Handle payment failure
            booking.setStatus(BookingStatus.FAILED);
            bookingRepository.save(booking);
            rollbackSeatReservation(show, seats);
            throw new BusinessLogicException("PAYMENT_FAILED",
                    "Payment processing failed with status: " + payment.getStatus());
        }

        return new BookingResponse(
                booking.getId(),
                show.getId(),
                booking.getStatus().name(),
                seats.stream().map(BookingSeat::getSeatLabel).collect(Collectors.toList()),
                amount,
                payment.getStatus().name(),
                payment.getReference()
        );
    }

    private void updateAvailableSeats(Show show, int seatsRequested) {
        // Optimistic update of available seats
        if (show.getAvailableSeats() == null || show.getAvailableSeats() < seatsRequested) {
            throw new BusinessLogicException ("SEATS_UNAVAILABLE", "Insufficient available seats");
        }
        show.setAvailableSeats(show.getAvailableSeats() - seatsRequested);
        showRepository.save(show);
    }

    private List<BookingSeat> reserveSeats(Booking booking, Show show, List<String> seatNumbers) {
        // Persist seats with unique constraint (show_id + seat_label)
        List<BookingSeat> seats = new ArrayList<>();
        try {
            for (String seatLabel : seatNumbers) {
                BookingSeat seat = new BookingSeat(booking, show, seatLabel);
                seats.add(bookingSeatRepository.save(seat));
            }
        } catch (DataIntegrityViolationException ex) {
            // Seats already booked
            throw new BusinessLogicException ("SEATS_UNAVAILABLE", "One or more selected seats are no longer available");
        }
        return seats;
    }

    private void rollbackSeatReservation(Show show, List<BookingSeat> seats) {
        show.setAvailableSeats(show.getAvailableSeats() + seats.size());
        showRepository.save(show);
        bookingSeatRepository.deleteAll(seats);
    }
}


