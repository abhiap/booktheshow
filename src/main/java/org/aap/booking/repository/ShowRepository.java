package org.aap.booking.repository;

import org.aap.booking.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByTheatreIdAndShowDateAndIsActiveTrue(Long theatreId, LocalDate showDate);

    @Query("SELECT s FROM Show s WHERE s.movie.id = :movieId AND s.theatre.city.id = :cityId AND s.showDate = :showDate AND s.isActive = true ORDER BY s.showTime")
    List<Show> findShowsByMovieAndCityAndDateOrderByTime(@Param("movieId") Long movieId, 
                                                         @Param("cityId") Long cityId, 
                                                         @Param("showDate") LocalDate showDate);
    
    @Query("SELECT s FROM Show s WHERE s.movie.id = :movieId AND s.theatre.id = :theatreId AND s.showDate = :showDate AND s.isActive = true ORDER BY s.showTime")
    List<Show> findShowsByMovieAndTheatreAndDateOrderByTime(@Param("movieId") Long movieId, 
                                                            @Param("theatreId") Long theatreId, 
                                                            @Param("showDate") LocalDate showDate);
}

