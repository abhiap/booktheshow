package org.aap.booking.repository;

import org.aap.booking.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT DISTINCT m FROM Movie m JOIN m.shows s WHERE s.theatre.city.id = :cityId AND s.showDate = :showDate AND m.isActive = true AND s.isActive = true")
    List<Movie> findMoviesByCityAndDate(@Param("cityId") Long cityId, @Param("showDate") java.time.LocalDate showDate);
}

