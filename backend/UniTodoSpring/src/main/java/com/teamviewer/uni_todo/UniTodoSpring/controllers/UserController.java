package com.teamviewer.uni_todo.UniTodoSpring.controllers;


import com.teamviewer.uni_todo.UniTodoSpring.domains.Role;
import com.teamviewer.uni_todo.UniTodoSpring.domains.UserDomain;
import com.teamviewer.uni_todo.UniTodoSpring.repositories.UserRepository;
import com.teamviewer.uni_todo.UniTodoSpring.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;




    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody UserDomain user) {
        if (userService.isUserPresent(user)) {
            return ResponseEntity.badRequest().body("Ο χρήστης υπάρχει ήδη!");
        }

        // Κρυπτογράφηση κωδικού και αποθήκευση
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userService.saveUser(user);

        return ResponseEntity.ok("Ο χρήστης δημιουργήθηκε επιτυχώς!");
    }


    @GetMapping("/check-username/{username}")
    public ResponseEntity<Map<String, Boolean>> checkUsernameExists(@PathVariable String username) {
        boolean exists = userRepository.existsByUsername(username);

        // ✅ Επιστρέφουμε JSON απάντηση {"exists": true/false}, όπως και στο check-email
        Map<String, Boolean> response = Collections.singletonMap("exists", exists);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/check-email/{email}")
    public ResponseEntity<Map<String, Boolean>> checkEmailExists(@PathVariable String email) {
        boolean exists = userRepository.existsByEmail(email);

        // ✅ Επιστρέφουμε JSON απάντηση {"exists": true/false}
        Map<String, Boolean> response = Collections.singletonMap("exists", exists);
        return ResponseEntity.ok(response);
    }

    // ✅ LOGOUT
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate(); // ✅ Διαγραφή session από τον server
        return ResponseEntity.ok("Logged out successfully!");
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "User not authenticated"));
        }

        UserDomain user = (UserDomain) authentication.getPrincipal();

        Map<String, Object> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("role", user.getRole()); // ✅ Επιστρέφουμε τον ρόλο του χρήστη

        // Προσθέτουμε τα στοιχεία της δεσμευμένης θέσης (αν υπάρχουν)
        if (user.getSelectedSpot() != null) {
            Map<String, Object> spot = new HashMap<>();
            spot.put("id", user.getSelectedSpot().getId());
            spot.put("spotNumber", user.getSelectedSpot().getSpotNumber());
            response.put("selectedSpot", spot);
        } else {
            response.put("selectedSpot", null);
        }

        return ResponseEntity.ok(response);
    }

}
