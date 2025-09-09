package org.aap.booking.service;

import org.aap.booking.dto.BrowseTheatresResponse;
import org.aap.booking.dto.TheatreDto;
import org.aap.booking.entity.City;
import org.aap.booking.entity.Movie;
import org.aap.booking.entity.Show;
import org.aap.booking.entity.Theatre;
import org.aap.booking.exception.ResourceNotFoundException;
import org.aap.booking.dto.BrowseTheatresRequest;
import org.aap.booking.dto.ShowTimingDto;
import org.aap.booking.repository.CityRepository;
import org.aap.booking.repository.MovieRepository;
import org.aap.booking.repository.ShowRepository;
import org.aap.booking.repository.TheatreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TheatresService {
    
    private final MovieRepository movieRepository;
    
    private final CityRepository cityRepository;
    
    private final TheatreRepository theatreRepository;
    
    private final ShowRepository showRepository;

    public TheatresService(MovieRepository movieRepository, CityRepository cityRepository, TheatreRepository theatreRepository, ShowRepository showRepository) {
        this.movieRepository = movieRepository;
        this.cityRepository = cityRepository;
        this.theatreRepository = theatreRepository;
        this.showRepository = showRepository;
    }

    public BrowseTheatresResponse browseTheatres(BrowseTheatresRequest request) {
        // Validate movie exists
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("MOVIE_NOT_FOUND", "Movie not found with ID: " + request.getMovieId()));

        // Validate city exists
        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("CITY_NOT_FOUND", "City not found with ID: " + request.getCityId()));

        // Get theatres showing the movie in the city on the requested date
        List<Theatre> theatres = theatreRepository.findTheatresByMovieAndCityAndDate(
                request.getMovieId(), request.getCityId(), request.getShowDate());
        
        // Get show details and convert to DTOs. If there are no theatres available for the movie,
        // returns empty list.
        List<TheatreDto> theatreDtos = theatres.stream()
                .map(theatre -> convertToTheatreDto(theatre, request.getMovieId(), request.getShowDate()))
                .collect(Collectors.toList());
        
        return new BrowseTheatresResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getLanguage(),
                city.getId(),
                city.getName(),
                request.getShowDate(),
                theatreDtos
        );
    }
    
    private TheatreDto convertToTheatreDto(Theatre theatre, Long movieId, java.time.LocalDate showDate) {
        // Get show timings for this theatre, movie, and date
        List<Show> shows = showRepository.findShowsByMovieAndTheatreAndDateOrderByTime(
                movieId, theatre.getId(), showDate);
        
        List<ShowTimingDto> showTimings = shows.stream()
                .map(this::convertToShowTimingDto)
                .collect(Collectors.toList());
        
        return new TheatreDto(
                theatre.getId(),
                theatre.getName(),
                theatre.getAddress(),
                theatre.getTotalScreens(),
                showTimings
        );
    }
    
    private ShowTimingDto convertToShowTimingDto(Show show) {
        return new ShowTimingDto(
                show.getId(),
                show.getShowTime(),
                show.getScreenNumber(),
                show.getTotalSeats(),
                show.getAvailableSeats(),
                show.getTicketPrice()
        );
    }
}
