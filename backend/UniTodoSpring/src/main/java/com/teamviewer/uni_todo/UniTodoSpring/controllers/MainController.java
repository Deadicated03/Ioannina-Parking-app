package com.teamviewer.uni_todo.UniTodoSpring.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000") // Επιτρέπει αιτήματα από το frontend
public class MainController {

    @GetMapping
    public ResponseEntity<String> homePage() {
        return ResponseEntity.ok("backend page");
    }
}