package org.app.booking.service;

import org.aap.booking.dto.BookingRequest;
import org.aap.booking.dto.BookingResponse;
import org.aap.booking.dto.PaymentStatus;
import org.aap.booking.entity.*;
import org.aap.booking.repository.BookingRepository;
import org.aap.booking.repository.BookingSeatRepository;
import org.aap.booking.repository.ShowRepository;
import org.aap.booking.service.BookingService;
import org.aap.booking.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private ShowRepository showRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingSeatRepository bookingSeatRepository;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private BookingService bookingService;

    private Show show;
    private Booking booking;

    @BeforeEach
    void setUp() {
        Movie movie = new Movie("Avengers: Endgame",
                "Desc", "English",
                LocalDate.of(2019, 4, 26), 181);
        movie.setId(10L);

        Theatre theatre = new Theatre();
        theatre.setId(5L);

        show = new Show(LocalDate.now(), LocalTime.of(10, 0), 1,
                200, 200, new BigDecimal("350.00"), movie, theatre);
        show.setId(1L);
        show.setVersion(0L);

        booking = new Booking(show, new BigDecimal("0.00"));
        booking.setId(100L);
    }

    @Test
    void bookTickets_HappyPath_ReturnsConfirmedBooking() {
        // Arrange
        BookingRequest request = new BookingRequest(1L, Arrays.asList("A1", "A2"), "user-1");

        when(showRepository.findById(1L)).thenReturn(Optional.of(show));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(inv -> {
            Booking b = inv.getArgument(0);
            b.setId(100L);
            return b;
        });
        when(bookingSeatRepository.save(any(BookingSeat.class))).thenAnswer(inv -> inv.getArgument(0));
        when(showRepository.save(any(Show.class))).thenAnswer(inv -> inv.getArgument(0));
        when(paymentService.initiatePayment(any(Booking.class))).thenAnswer(inv -> {
            Payment p = new Payment();
            p.setId(1000L);
            p.setStatus(PaymentStatus.SUCCESS);
            return p;
        });

        // Act
        BookingResponse response = bookingService.bookTickets(request);

        // Assert
        assertNotNull(response);
        assertEquals(100L, response.getBookingId());
        assertEquals(1L, response.getShowId());
        assertEquals("CONFIRMED", response.getStatus());
        assertEquals(Arrays.asList("A1", "A2"), response.getSeats());
        assertEquals(new BigDecimal("700.00"), response.getAmount());
        assertEquals("SUCCESS", response.getPaymentStatus());

        // available seats reduced
        assertEquals(198, show.getAvailableSeats());

        verify(showRepository).findById(1L);
        verify(bookingRepository, atLeastOnce()).save(any(Booking.class));
        verify(bookingSeatRepository, times(2)).save(any(BookingSeat.class));
        verify(paymentService, atLeastOnce()).initiatePayment(any(Booking.class));
        verify(showRepository).save(any(Show.class));
    }

    @Test
    void bookTickets_NoSeatsSelected_ThrowsException() {
        // Arrange
        BookingRequest request = new BookingRequest(1L, Collections.emptyList(), "user-1");
        when(showRepository.findById(1L)).thenReturn(Optional.of(show));

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> bookingService.bookTickets(request));
        assertEquals("At least one seat must be selected", ex.getMessage());

        verify(bookingRepository, never()).save(any());
        verify(bookingSeatRepository, never()).save(any());
    }

    @Test
    void bookTickets_InsufficientAvailableSeats_ThrowsException() {
        // Arrange
        show.setAvailableSeats(1);
        BookingRequest request = new BookingRequest(1L, Arrays.asList("A1", "A2"), "user-1");
        when(showRepository.findById(1L)).thenReturn(Optional.of(show));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(bookingSeatRepository.save(any(BookingSeat.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> bookingService.bookTickets(request));
        assertEquals("Insufficient available seats", ex.getMessage());

        //verify
    }

    @Test
    void bookTickets_SeatAlreadyBooked_ThrowsSeatUnavailable() {
        // Arrange
        BookingRequest request = new BookingRequest(1L, Arrays.asList("A1", "A2"), "user-1");
        when(showRepository.findById(1L)).thenReturn(Optional.of(show));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(bookingSeatRepository.save(any(BookingSeat.class)))
                .thenAnswer(inv -> inv.getArgument(0))
                .thenThrow(new DataIntegrityViolationException("duplicate"));

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> bookingService.bookTickets(request));
        assertEquals("One or more selected seats are no longer available", ex.getMessage());

        verify(bookingSeatRepository, times(2)).save(any(BookingSeat.class));
    }

    @Test
    void bookTickets_ShowNotFound_ThrowsException() {
        // Arrange
        BookingRequest request = new BookingRequest(999L, Arrays.asList("A1"), "user-1");
        when(showRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> bookingService.bookTickets(request));
        assertEquals("Show not found with ID: 999", ex.getMessage());

        verify(bookingRepository, never()).save(any());
        verify(bookingSeatRepository, never()).save(any());
        verify(paymentService, never()).initiatePayment(any());
    }
}


