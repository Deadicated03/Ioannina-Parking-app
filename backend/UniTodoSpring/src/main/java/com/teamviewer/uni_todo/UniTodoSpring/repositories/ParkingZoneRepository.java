package com.teamviewer.uni_todo.UniTodoSpring.repositories;

import com.teamviewer.uni_todo.UniTodoSpring.domains.ParkingSpot;
import com.teamviewer.uni_todo.UniTodoSpring.domains.ParkingZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParkingZoneRepository extends JpaRepository<ParkingZone,Long> {
    List<ParkingZone> findByParkingAreaId(@Param("areaId") Long areaId);

}
