package org.aap.booking.config;

import org.aap.booking.entity.City;
import org.aap.booking.entity.Movie;
import org.aap.booking.entity.Show;
import org.aap.booking.entity.Theatre;
import org.aap.booking.repository.CityRepository;
import org.aap.booking.repository.MovieRepository;
import org.aap.booking.repository.ShowRepository;
import org.aap.booking.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private CityRepository cityRepository;
    
    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private TheatreRepository theatreRepository;
    
    @Autowired
    private ShowRepository showRepository;
    
    @Override
    public void run(String... args) throws Exception {
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        // Create cities
        City mumbai = new City("Mumbai", "Maharashtra", "India");
        City delhi = new City("Delhi", "Delhi", "India");
        City bangalore = new City("Bangalore", "Karnataka", "India");
        
        mumbai = cityRepository.save(mumbai);
        delhi = cityRepository.save(delhi);
        bangalore = cityRepository.save(bangalore);
        
        // Create movies
        Movie avengers = new Movie("Avengers: Endgame",
                "After the devastating events of Avengers: Infinity War, the universe is in ruins.", 
                "English", LocalDate.of(2019, 4, 26), 181);
        
        Movie bahubali = new Movie("Baahubali 2: The Conclusion",
                "When Shiva, the son of Bahubali, learns about his heritage, he begins to look for answers.", 
                "Telugu", LocalDate.of(2017, 4, 28), 167);
        
        Movie dangal = new Movie("Dangal",
                "Former wrestler Mahavir Singh Phogat and his two wrestler daughters struggle for glory.", 
                "Hindi", LocalDate.of(2016, 12, 23), 161);
        
        avengers = movieRepository.save(avengers);
        bahubali = movieRepository.save(bahubali);
        dangal = movieRepository.save(dangal);
        
        // Create theatres
        Theatre pvrMumbai = new Theatre("PVR Cinemas - Phoenix Mills",
                "Phoenix Mills, Lower Parel, Mumbai", 8, mumbai);
        
        Theatre inoxdelhi = new Theatre("INOX - R City Mall",
                "R City Mall, Ghatkopar, Mumbai", 6, mumbai);
        
        Theatre cinedelhi = new Theatre("Cinepolis - Select City Walk",
                "Select City Walk, Saket, Delhi", 7, delhi);
        
        Theatre pvrbengalore = new Theatre("PVR - Forum Mall",
                "Forum Mall, Koramangala, Bangalore", 9, bangalore);
        
        pvrMumbai = theatreRepository.save(pvrMumbai);
        inoxdelhi = theatreRepository.save(inoxdelhi);
        cinedelhi = theatreRepository.save(cinedelhi);
        pvrbengalore = theatreRepository.save(pvrbengalore);
        
        // Create shows for today and tomorrow
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        
        // Shows for Avengers: Endgame
        createShow(avengers, pvrMumbai, today, LocalTime.of(10, 0), 1, 200, 150, new BigDecimal("350.00"));
        createShow(avengers, pvrMumbai, today, LocalTime.of(14, 30), 2, 180, 120, new BigDecimal("400.00"));
        createShow(avengers, pvrMumbai, today, LocalTime.of(19, 0), 1, 200, 180, new BigDecimal("450.00"));
        
        createShow(avengers, inoxdelhi, today, LocalTime.of(11, 30), 1, 150, 100, new BigDecimal("300.00"));
        createShow(avengers, inoxdelhi, today, LocalTime.of(16, 0), 2, 150, 140, new BigDecimal("350.00"));
        
        createShow(avengers, cinedelhi, today, LocalTime.of(12, 0), 1, 220, 200, new BigDecimal("380.00"));
        createShow(avengers, cinedelhi, today, LocalTime.of(17, 30), 2, 220, 180, new BigDecimal("420.00"));
        
        // Shows for Baahubali 2
        createShow(bahubali, pvrMumbai, today, LocalTime.of(9, 30), 3, 200, 150, new BigDecimal("250.00"));
        createShow(bahubali, pvrMumbai, today, LocalTime.of(15, 0), 3, 200, 120, new BigDecimal("300.00"));
        
        createShow(bahubali, inoxdelhi, today, LocalTime.of(10, 0), 3, 150, 100, new BigDecimal("200.00"));
        createShow(bahubali, inoxdelhi, today, LocalTime.of(18, 0), 3, 150, 130, new BigDecimal("250.00"));
        
        // Shows for Dangal
        createShow(dangal, cinedelhi, today, LocalTime.of(10, 30), 3, 220, 200, new BigDecimal("280.00"));
        createShow(dangal, cinedelhi, today, LocalTime.of(16, 30), 3, 220, 180, new BigDecimal("320.00"));
        
        createShow(dangal, pvrbengalore, today, LocalTime.of(11, 0), 1, 250, 200, new BigDecimal("300.00"));
        createShow(dangal, pvrbengalore, today, LocalTime.of(18, 30), 2, 250, 220, new BigDecimal("350.00"));
        
        // Tomorrow's shows
        createShow(avengers, pvrMumbai, tomorrow, LocalTime.of(10, 0), 1, 200, 200, new BigDecimal("350.00"));
        createShow(avengers, pvrMumbai, tomorrow, LocalTime.of(14, 30), 2, 180, 180, new BigDecimal("400.00"));
        createShow(avengers, inoxdelhi, tomorrow, LocalTime.of(11, 30), 1, 150, 150, new BigDecimal("300.00"));
        createShow(bahubali, pvrMumbai, tomorrow, LocalTime.of(9, 30), 3, 200, 200, new BigDecimal("250.00"));
        createShow(dangal, pvrbengalore, tomorrow, LocalTime.of(11, 0), 1, 250, 250, new BigDecimal("300.00"));
    }
    
    private void createShow(Movie movie, Theatre theatre, LocalDate showDate, LocalTime showTime, 
                           Integer screenNumber, Integer totalSeats, Integer availableSeats, BigDecimal ticketPrice) {
        Show show = new Show(showDate, showTime, screenNumber, totalSeats, availableSeats, ticketPrice, movie, theatre);
        showRepository.save(show);
    }
}
