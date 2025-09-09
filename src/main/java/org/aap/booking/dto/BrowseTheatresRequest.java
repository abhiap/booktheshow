package org.aap.booking.dto;

import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class BrowseTheatresRequest {
    
    @NotNull(message = "Movie ID is required")
    private Long movieId;
    
    @NotNull(message = "City ID is required")
    private Long cityId;
    
    @NotNull(message = "Show date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate showDate;
    
    public BrowseTheatresRequest() {}
    
    public BrowseTheatresRequest(Long movieId, Long cityId, LocalDate showDate) {
        this.movieId = movieId;
        this.cityId = cityId;
        this.showDate = showDate;
    }
    
    public Long getMovieId() {
        return movieId;
    }
    
    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
    
    public Long getCityId() {
        return cityId;
    }
    
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
    
    public LocalDate getShowDate() {
        return showDate;
    }
    
    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
    }
}