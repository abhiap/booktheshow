package org.aap.booking.repository;

import org.aap.booking.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    
    Optional<City> findByNameIgnoreCase(String name);

    @Query("SELECT c FROM City c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')) AND c.isActive = true")
    List<City> findByNameContainingIgnoreCaseAndIsActiveTrue(@Param("name") String name);
}

