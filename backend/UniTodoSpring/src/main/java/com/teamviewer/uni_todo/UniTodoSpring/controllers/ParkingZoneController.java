package com.teamviewer.uni_todo.UniTodoSpring.controllers;

import com.teamviewer.uni_todo.UniTodoSpring.domains.ParkingArea;
import com.teamviewer.uni_todo.UniTodoSpring.domains.ParkingZone;
import com.teamviewer.uni_todo.UniTodoSpring.repositories.ParkingAreaRepository;
import com.teamviewer.uni_todo.UniTodoSpring.repositories.ParkingZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

import java.util.List;

@RestController
@RequestMapping("/api/parking/zones")
@CrossOrigin(origins = "http://localhost:3000")
public class ParkingZoneController {

    @Autowired
    private ParkingZoneRepository parkingZoneRepository;

    @Autowired
    private ParkingAreaRepository parkingAreaRepository;

    // 🔹 1. Επιστροφή όλων των ParkingZones για συγκεκριμένο ParkingArea
    @GetMapping("/{areaId}")
    public ResponseEntity<List<ParkingZone>> getZonesByArea(@PathVariable Long areaId) {
        return ResponseEntity.ok(parkingZoneRepository.findByParkingAreaId(areaId));
    }

}