package org.app.booking.service;

import org.aap.booking.dto.BrowseTheatresRequest;
import org.aap.booking.dto.BrowseTheatresResponse;
import org.aap.booking.dto.ShowTimingDto;
import org.aap.booking.dto.TheatreDto;
import org.aap.booking.entity.City;
import org.aap.booking.entity.Movie;
import org.aap.booking.entity.Show;
import org.aap.booking.entity.Theatre;
import org.aap.booking.exception.ResourceNotFoundException;
import org.aap.booking.repository.CityRepository;
import org.aap.booking.repository.MovieRepository;
import org.aap.booking.repository.ShowRepository;
import org.aap.booking.repository.TheatreRepository;
import org.aap.booking.service.TheatresService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TheatresServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private TheatreRepository theatreRepository;

    @Mock
    private ShowRepository showRepository;

    @InjectMocks
    private TheatresService theatresService;

    private Movie testMovie;
    private City testCity;
    private Theatre testTheatre1;
    private Theatre testTheatre2;
    private Show testShow1;
    private Show testShow2;
    private BrowseTheatresRequest testRequest;

    @BeforeEach
    void setUp() {
        // Setup test data
        testCity = new City("Mumbai", "Maharashtra", "India");
        testCity.setId(1L);

        testMovie = new Movie("Avengers: Endgame", 
                "After the devastating events of Avengers: Infinity War, the universe is in ruins.", 
                "English", LocalDate.of(2019, 4, 26), 181);
        testMovie.setId(1L);

        testTheatre1 = new Theatre("PVR Cinemas - Phoenix Mills", 
                "Phoenix Mills, Lower Parel, Mumbai", 8, testCity);
        testTheatre1.setId(1L);

        testTheatre2 = new Theatre("INOX - R City Mall", 
                "R City Mall, Ghatkopar, Mumbai", 6, testCity);
        testTheatre2.setId(2L);

        testShow1 = new Show(LocalDate.now(), LocalTime.of(10, 0), 1, 200, 150, 
                new BigDecimal("350.00"), testMovie, testTheatre1);
        testShow1.setId(1L);

        testShow2 = new Show(LocalDate.now(), LocalTime.of(14, 30), 2, 180, 120, 
                new BigDecimal("400.00"), testMovie, testTheatre1);
        testShow2.setId(2L);

        testRequest = new BrowseTheatresRequest(1L, 1L, LocalDate.now());
    }

    @Test
    void browseTheatres_HappyPath_ReturnsCorrectResponse() {
        // Arrange
        List<Theatre> theatres = Arrays.asList(testTheatre1, testTheatre2);
        List<Show> showsForTheatre1 = Arrays.asList(testShow1, testShow2);
        List<Show> showsForTheatre2 = Collections.emptyList();

        when(movieRepository.findById(1L)).thenReturn(Optional.of(testMovie));
        when(cityRepository.findById(1L)).thenReturn(Optional.of(testCity));
        when(theatreRepository.findTheatresByMovieAndCityAndDate(1L, 1L, LocalDate.now()))
                .thenReturn(theatres);
        when(showRepository.findShowsByMovieAndTheatreAndDateOrderByTime(1L, 1L, LocalDate.now()))
                .thenReturn(showsForTheatre1);
        when(showRepository.findShowsByMovieAndTheatreAndDateOrderByTime(1L, 2L, LocalDate.now()))
                .thenReturn(showsForTheatre2);

        // Act
        BrowseTheatresResponse response = theatresService.browseTheatres(testRequest);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getMovieId());
        assertEquals("Avengers: Endgame", response.getMovieTitle());
        assertEquals(1L, response.getCityId());
        assertEquals(LocalDate.now(), response.getShowDate());
        assertEquals(2, response.getTheatres().size());

        // Verify first theatre
        TheatreDto firstTheatre = response.getTheatres().get(0);
        assertEquals(1L, firstTheatre.getTheatreId());
        assertEquals("PVR Cinemas - Phoenix Mills", firstTheatre.getTheatreName());
        assertEquals(2, firstTheatre.getShowTimings().size());

        // Verify show timings
        ShowTimingDto firstShow = firstTheatre.getShowTimings().get(0);
        assertEquals(1L, firstShow.getShowId());
        assertEquals(LocalTime.of(10, 0), firstShow.getShowTime());
        assertEquals(1, firstShow.getScreenNumber());
        assertEquals(200, firstShow.getTotalSeats());
        assertEquals(150, firstShow.getAvailableSeats());
        assertEquals(new BigDecimal("350.00"), firstShow.getTicketPrice());

        // Verify second theatre has no shows
        TheatreDto secondTheatre = response.getTheatres().get(1);
        assertEquals("INOX - R City Mall", secondTheatre.getTheatreName());
        assertTrue(secondTheatre.getShowTimings().isEmpty());

        // Verify repository interactions
        verify(movieRepository).findById(1L);
        verify(cityRepository).findById(1L);
        verify(theatreRepository).findTheatresByMovieAndCityAndDate(1L, 1L, LocalDate.now());
        verify(showRepository).findShowsByMovieAndTheatreAndDateOrderByTime(1L, 1L, LocalDate.now());
        verify(showRepository).findShowsByMovieAndTheatreAndDateOrderByTime(1L, 2L, LocalDate.now());
    }

    @Test
    void browseTheatres_NoTheatresFound_ReturnsEmptyTheatresList() {
        // Arrange
        when(movieRepository.findById(1L)).thenReturn(Optional.of(testMovie));
        when(cityRepository.findById(1L)).thenReturn(Optional.of(testCity));
        when(theatreRepository.findTheatresByMovieAndCityAndDate(1L, 1L, LocalDate.now()))
                .thenReturn(Collections.emptyList());

        // Act
        BrowseTheatresResponse response = theatresService.browseTheatres(testRequest);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getMovieId());
        assertEquals("Avengers: Endgame", response.getMovieTitle());
        assertEquals(1L, response.getCityId());
        assertEquals("Mumbai", response.getCityName());
        assertTrue(response.getTheatres().isEmpty());

        // Verify repository
    }

    @Test
    void browseTheatres_TheatreWithNoShows_ReturnsTheatreWithEmptyShowTimings() {
        // Arrange
        List<Theatre> theatres = Arrays.asList(testTheatre1);
        List<Show> emptyShows = Collections.emptyList();

        when(movieRepository.findById(1L)).thenReturn(Optional.of(testMovie));
        when(cityRepository.findById(1L)).thenReturn(Optional.of(testCity));
        when(theatreRepository.findTheatresByMovieAndCityAndDate(1L, 1L, LocalDate.now()))
                .thenReturn(theatres);
        when(showRepository.findShowsByMovieAndTheatreAndDateOrderByTime(1L, 1L, LocalDate.now()))
                .thenReturn(emptyShows);

        // Act
        BrowseTheatresResponse response = theatresService.browseTheatres(testRequest);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getTheatres().size());

        TheatreDto theatre = response.getTheatres().get(0);
        assertEquals(1L, theatre.getTheatreId());
        assertTrue(theatre.getShowTimings().isEmpty());

        verify(showRepository).findShowsByMovieAndTheatreAndDateOrderByTime(1L, 1L, LocalDate.now());
    }

    @Test
    void browseTheatres_MultipleShowsSameTheatre_ReturnsAllShowsOrderedByTime() {
        // Arrange
        Show show3 = new Show(LocalDate.now(), LocalTime.of(19, 0), 1, 200, 180, 
                new BigDecimal("450.00"), testMovie, testTheatre1);
        show3.setId(3L);

        List<Theatre> theatres = Arrays.asList(testTheatre1);
        List<Show> shows = Arrays.asList(testShow1, testShow2, show3);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(testMovie));
        when(cityRepository.findById(1L)).thenReturn(Optional.of(testCity));
        when(theatreRepository.findTheatresByMovieAndCityAndDate(1L, 1L, LocalDate.now()))
                .thenReturn(theatres);
        when(showRepository.findShowsByMovieAndTheatreAndDateOrderByTime(1L, 1L, LocalDate.now()))
                .thenReturn(shows);

        // Act
        BrowseTheatresResponse response = theatresService.browseTheatres(testRequest);

        // Assert
        assertNotNull(response);

        TheatreDto theatre = response.getTheatres().get(0);
        assertEquals(3, theatre.getShowTimings().size());

        // Verify shows are ordered by time
        List<ShowTimingDto> showTimings = theatre.getShowTimings();
        assertEquals(LocalTime.of(10, 0), showTimings.get(0).getShowTime());
        assertEquals(LocalTime.of(14, 30), showTimings.get(1).getShowTime());
        assertEquals(LocalTime.of(19, 0), showTimings.get(2).getShowTime());
    }

    @Test
    void browseTheatres_InvalidMovieId_ThrowsResourceNotFoundException() {
        // Arrange
        BrowseTheatresRequest invalidRequest = new BrowseTheatresRequest(999L, 1L, LocalDate.now());
        when(movieRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            theatresService.browseTheatres(invalidRequest);
        });

        assertEquals("Movie not found with ID: 999", exception.getMessage());
        verify(movieRepository).findById(999L);
        verify(cityRepository, never()).findById(anyLong());
    }

}
