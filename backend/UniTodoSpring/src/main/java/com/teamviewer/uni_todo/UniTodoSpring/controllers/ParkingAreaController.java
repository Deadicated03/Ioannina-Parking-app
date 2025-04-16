package com.teamviewer.uni_todo.UniTodoSpring.controllers;

import com.teamviewer.uni_todo.UniTodoSpring.domains.ParkingArea;
import com.teamviewer.uni_todo.UniTodoSpring.repositories.ParkingAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking/areas")
@CrossOrigin(origins = "http://localhost:3000")
public class ParkingAreaController {

    @Autowired
    private ParkingAreaRepository parkingAreaRepository;

    // 🔹 1. Επιστροφή όλων των ParkingAreas
    @GetMapping
    public ResponseEntity<List<ParkingArea>> getAllAreas() {
        return ResponseEntity.ok(parkingAreaRepository.findAll());
    }

}