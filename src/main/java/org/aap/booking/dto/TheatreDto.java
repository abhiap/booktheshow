package org.aap.booking.dto;

import java.util.List;

public class TheatreDto {
    
    private Long theatreId;
    
    private String theatreName;
    
    private String address;

    private Integer totalScreens;
    
    private List<ShowTimingDto> showTimings;
    
    public TheatreDto() {}
    
    public TheatreDto(Long theatreId, String theatreName, String address,
                      Integer totalScreens,
                     List<ShowTimingDto> showTimings) {
        this.theatreId = theatreId;
        this.theatreName = theatreName;
        this.address = address;
        this.totalScreens = totalScreens;
        this.showTimings = showTimings;
    }
    
    public Long getTheatreId() {
        return theatreId;
    }
    
    public void setTheatreId(Long theatreId) {
        this.theatreId = theatreId;
    }
    
    public String getTheatreName() {
        return theatreName;
    }
    
    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
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
    
    public List<ShowTimingDto> getShowTimings() {
        return showTimings;
    }
    
    public void setShowTimings(List<ShowTimingDto> showTimings) {
        this.showTimings = showTimings;
    }
}
