package com.teamviewer.uni_todo.UniTodoSpring.controllers;

import com.teamviewer.uni_todo.UniTodoSpring.repositories.UserRepository;
// import και άλλα repository αν θέλεις να τα διαγράψεις όλα
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.teamviewer.uni_todo.UniTodoSpring.domains.Role;


@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    // Αν έχεις και TaskRepository, ProjectRepository κ.λπ., τα κάνεις επίσης @Autowired

    @DeleteMapping("/clear-database")
    public ResponseEntity<String> clearDatabase() {
        // Διαγραφή μόνο των χρηστών με ρόλο USER
        userRepository.deleteByRole(Role.USER);
        // Εδώ μπορείς να προσθέσεις και διαγραφή δεδομένων από άλλα repositories, αν το απαιτείς.

        return ResponseEntity.ok("Database cleared successfully! (Only non-admin users removed)");
    }

}
