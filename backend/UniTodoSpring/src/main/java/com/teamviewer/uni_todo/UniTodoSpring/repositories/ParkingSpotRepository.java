package com.teamviewer.uni_todo.UniTodoSpring.repositories;

import com.teamviewer.uni_todo.UniTodoSpring.domains.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    List<ParkingSpot> findByParkingZoneId(@Param("zoneId") Long zoneId);

    @PreAuthorize("isAuthenticated()")
    @Query("SELECT p FROM ParkingSpot p WHERE p.parkingZone.id = :zoneId AND p.isAvailable = true")
    List<ParkingSpot> findAvailableSpotsByZoneId(@Param("zoneId") Long zoneId);
}
