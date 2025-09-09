package org.aap.booking.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class BookingRequest {

    @NotNull(message = "Show ID is required")
    private Long showId;

    @NotEmpty(message = "At least one seat must be selected")
    private List<String> seatNumbers;

    @NotNull(message = "User identifier is required")
    private String userId; // placeholder for future auth integration

    public BookingRequest() {}

    public BookingRequest(Long showId, List<String> seatNumbers, String userId) {
        this.showId = showId;
        this.seatNumbers = seatNumbers;
        this.userId = userId;
    }

    public Long getShowId() { return showId; }
    public void setShowId(Long showId) { this.showId = showId; }
    public List<String> getSeatNumbers() { return seatNumbers; }
    public void setSeatNumbers(List<String> seatNumbers) { this.seatNumbers = seatNumbers; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}


