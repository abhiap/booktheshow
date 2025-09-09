package org.aap.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "theatres")
public class Theatre {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Theatre name is required")
    @Size(max = 200, message = "Theatre name must not exceed 200 characters")
    @Column(nullable = false)
    private String name;
    
    @Size(max = 500, message = "Address must not exceed 500 characters")
    private String address;

    @Column(name = "total_screens")
    private Integer totalScreens;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;
    
    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Show> shows;
    
    public Theatre() {}
    
    public Theatre(String name, String address,
                   Integer totalScreens, City city) {
        this.name = name;
        this.address = address;
        this.totalScreens = totalScreens;
        this.city = city;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTotalScreens() {
        return totalScreens;
    }
    
    public void setTotalScreens(Integer totalScreens) {
        this.totalScreens = totalScreens;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public City getCity() {
        return city;
    }
    
    public void setCity(City city) {
        this.city = city;
    }
    
    public List<Show> getShows() {
        return shows;
    }
    
    public void setShows(List<Show> shows) {
        this.shows = shows;
    }
}

