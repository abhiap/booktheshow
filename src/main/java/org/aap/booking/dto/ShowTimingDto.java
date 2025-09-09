package org.aap.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalTime;

public class ShowTimingDto {
    
    private Long showId;
    
    @JsonFormat(pattern = "HH:mm")
    private LocalTime showTime;
    
    private Integer screenNumber;
    
    private Integer totalSeats;
    
    private Integer availableSeats;
    
    private BigDecimal ticketPrice;
    
    public ShowTimingDto() {}
    
    public ShowTimingDto(Long showId, LocalTime showTime, Integer screenNumber, 
                        Integer totalSeats, Integer availableSeats, BigDecimal ticketPrice) {
        this.showId = showId;
        this.showTime = showTime;
        this.screenNumber = screenNumber;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.ticketPrice = ticketPrice;
    }
    
    public Long getShowId() {
        return showId;
    }
    
    public void setShowId(Long showId) {
        this.showId = showId;
    }
    
    public LocalTime getShowTime() {
        return showTime;
    }
    
    public void setShowTime(LocalTime showTime) {
        this.showTime = showTime;
    }
    
    public Integer getScreenNumber() {
        return screenNumber;
    }
    
    public void setScreenNumber(Integer screenNumber) {
        this.screenNumber = screenNumber;
    }
    
    public Integer getTotalSeats() {
        return totalSeats;
    }
    
    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }
    
    public Integer getAvailableSeats() {
        return availableSeats;
    }
    
    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }
    
    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }
    
    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}