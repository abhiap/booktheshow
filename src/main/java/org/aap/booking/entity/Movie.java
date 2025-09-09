package org.aap.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Movie title is required")
    @Size(max = 200, message = "Movie title must not exceed 200 characters")
    @Column(nullable = false)
    private String title;
    
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
    
    @Size(max = 50, message = "Language must not exceed 50 characters")
    private String language;

    @Column(name = "release_date")
    private LocalDate releaseDate;
    
    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Show> shows;
    
    public Movie() {}
    
    public Movie(String title, String description, String language,
                 LocalDate releaseDate, Integer durationMinutes) {
        this.title = title;
        this.description = description;
        this.language = language;
        this.releaseDate = releaseDate;
        this.durationMinutes = durationMinutes;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    public Integer getDurationMinutes() {
        return durationMinutes;
    }
    
    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public List<Show> getShows() {
        return shows;
    }
    
    public void setShows(List<Show> shows) {
        this.shows = shows;
    }
}

