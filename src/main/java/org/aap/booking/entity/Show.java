package org.aap.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "shows")
public class Show {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Show date is required")
    @Column(name = "show_date", nullable = false)
    private LocalDate showDate;
    
    @NotNull(message = "Show time is required")
    @Column(name = "show_time", nullable = false)
    private LocalTime showTime;
    
    @Column(name = "screen_number")
    private Integer screenNumber;
    
    @Column(name = "total_seats")
    private Integer totalSeats;
    
    @Column(name = "available_seats")
    private Integer availableSeats;
    
    @Column(name = "ticket_price", precision = 10, scale = 2)
    private BigDecimal ticketPrice;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Version
    @Column(name = "version")
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;
    
    public Show() {}
    
    public Show(LocalDate showDate, LocalTime showTime, Integer screenNumber, 
                Integer totalSeats, Integer availableSeats, BigDecimal ticketPrice, 
                Movie movie, Theatre theatre) {
        this.showDate = showDate;
        this.showTime = showTime;
        this.screenNumber = screenNumber;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.ticketPrice = ticketPrice;
        this.movie = movie;
        this.theatre = theatre;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDate getShowDate() {
        return showDate;
    }
    
    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
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
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    
    public Movie getMovie() {
        return movie;
    }
    
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    
    public Theatre getTheatre() {
        return theatre;
    }
    
    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }
}

