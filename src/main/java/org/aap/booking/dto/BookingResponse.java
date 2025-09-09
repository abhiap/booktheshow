package org.aap.booking.dto;

import java.math.BigDecimal;
import java.util.List;

public class BookingResponse {
    private Long bookingId;
    private Long showId;
    private String status;
    private List<String> seats;
    private BigDecimal amount;
    private String paymentStatus;
    private String paymentReference;

    public BookingResponse() {}

    public BookingResponse(Long bookingId, Long showId, String status, List<String> seats,
                           BigDecimal amount, String paymentStatus, String paymentReference) {
        this.bookingId = bookingId;
        this.showId = showId;
        this.status = status;
        this.seats = seats;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paymentReference = paymentReference;
    }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    public Long getShowId() { return showId; }
    public void setShowId(Long showId) { this.showId = showId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<String> getSeats() { return seats; }
    public void setSeats(List<String> seats) { this.seats = seats; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public String getPaymentReference() { return paymentReference; }
    public void setPaymentReference(String paymentReference) { this.paymentReference = paymentReference; }
}


