package org.aap.booking.repository;

import org.aap.booking.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    
    List<Theatre> findByCityIdAndIsActiveTrue(Long cityId);

    @Query("SELECT DISTINCT t FROM Theatre t JOIN t.shows s WHERE s.movie.id = :movieId AND s.theatre.city.id = :cityId AND s.showDate = :showDate AND t.isActive = true AND s.isActive = true")
    List<Theatre> findTheatresByMovieAndCityAndDate(@Param("movieId") Long movieId, 
                                                    @Param("cityId") Long cityId, 
                                                    @Param("showDate") java.time.LocalDate showDate);
}

