package org.aap.booking.controller;

import org.aap.booking.dto.BookingRequest;
import org.aap.booking.dto.BookingResponse;
import org.aap.booking.dto.ErrorResponse;
import org.aap.booking.exception.BusinessLogicException;
import org.aap.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Req: Book movie tickets by selecting a theatre, timing, and preferred seats for the day
    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequest request) {
        try {
            BookingResponse response = bookingService.bookTickets(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BusinessLogicException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getErrorCode(), ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error");
        }
    }
}


