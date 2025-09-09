package org.aap.booking.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "booking_seats", uniqueConstraints = {
        @UniqueConstraint(name = "uk_show_seat", columnNames = {"show_id", "seat_label"})
})
public class BookingSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @Column(name = "seat_label", nullable = false, length = 20)
    private String seatLabel; // e.g., A10

    public BookingSeat() {}

    public BookingSeat(Booking booking, Show show, String seatLabel) {
        this.booking = booking;
        this.show = show;
        this.seatLabel = seatLabel;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
    public Show getShow() { return show; }
    public void setShow(Show show) { this.show = show; }
    public String getSeatLabel() { return seatLabel; }
    public void setSeatLabel(String seatLabel) { this.seatLabel = seatLabel; }
}


