package org.aap.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cities")
public class City {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "City name is required")
    @Size(max = 100, message = "City name must not exceed 100 characters")
    @Column(nullable = false, unique = true)
    private String name;
    
    @Size(max = 50, message = "State must not exceed 50 characters")
    private String state;
    
    @Size(max = 50, message = "Country must not exceed 50 characters")
    private String country;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    public City() {}
    
    public City(String name, String state, String country) {
        this.name = name;
        this.state = state;
        this.country = country;
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
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}

