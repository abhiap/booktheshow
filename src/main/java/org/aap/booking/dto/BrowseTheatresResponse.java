package org.aap.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

public class BrowseTheatresResponse {
    
    private Long movieId;
    
    private String movieTitle;
    
    private String movieLanguage;

    private Long cityId;
    
    private String cityName;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate showDate;
    
    private List<TheatreDto> theatres;

    public BrowseTheatresResponse() {}
    
    public BrowseTheatresResponse(Long movieId, String movieTitle, String movieLanguage,
                                  Long cityId, String cityName,
                                 LocalDate showDate, List<TheatreDto> theatres) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieLanguage = movieLanguage;
        this.cityId = cityId;
        this.cityName = cityName;
        this.showDate = showDate;
        this.theatres = theatres;
    }
    
    public Long getMovieId() {
        return movieId;
    }
    
    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
    
    public String getMovieTitle() {
        return movieTitle;
    }
    
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
    
    public String getMovieLanguage() {
        return movieLanguage;
    }
    
    public void setMovieLanguage(String movieLanguage) {
        this.movieLanguage = movieLanguage;
    }

    public Long getCityId() {
        return cityId;
    }
    
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
    
    public String getCityName() {
        return cityName;
    }
    
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    public LocalDate getShowDate() {
        return showDate;
    }
    
    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
    }
    
    public List<TheatreDto> getTheatres() {
        return theatres;
    }
    
    public void setTheatres(List<TheatreDto> theatres) {
        this.theatres = theatres;
    }

}
