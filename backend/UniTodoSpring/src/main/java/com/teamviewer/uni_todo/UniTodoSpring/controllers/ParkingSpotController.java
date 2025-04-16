package com.teamviewer.uni_todo.UniTodoSpring.controllers;

import com.teamviewer.uni_todo.UniTodoSpring.domains.ParkingSpot;
import com.teamviewer.uni_todo.UniTodoSpring.domains.ParkingZone;
import com.teamviewer.uni_todo.UniTodoSpring.domains.UserDomain;
import com.teamviewer.uni_todo.UniTodoSpring.repositories.ParkingSpotRepository;
import com.teamviewer.uni_todo.UniTodoSpring.repositories.ParkingZoneRepository;
import com.teamviewer.uni_todo.UniTodoSpring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/parking/spots")
@CrossOrigin(origins = "http://localhost:3000")
public class ParkingSpotController {

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Autowired
    private ParkingZoneRepository parkingZoneRepository;

    @Autowired
    private UserRepository userRepository;

    // 🔹 1. Επιστροφή όλων των ParkingSpots για συγκεκριμένο ParkingZone
    @GetMapping("/zone/{zoneId}")
    public ResponseEntity<List<ParkingSpot>> getSpotsByZone(@PathVariable Long zoneId) {
        return ResponseEntity.ok(parkingSpotRepository.findByParkingZoneId(zoneId));
    }

    // 🔹 2. Επιστροφή ΜΟΝΟ των διαθέσιμων ParkingSpots για συγκεκριμένο ParkingZone
    @GetMapping("/zone/{zoneId}/available")
    public ResponseEntity<List<ParkingSpot>> getAvailableSpots(@PathVariable Long zoneId) {
        List<ParkingSpot> availableSpots = parkingSpotRepository.findByParkingZoneId(zoneId)
                .stream().filter(ParkingSpot::isAvailable).toList();
        return ResponseEntity.ok(availableSpots);
    }

    // 🔹 3. Κράτηση (παρκάρισμα) ParkingSpot ΜΟΝΟ ΜΙΑΣ θέσης ανά χρήστη
    @PostMapping("/{spotId}/reserve")
    public ResponseEntity<String> reserveSpot(@PathVariable Long spotId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("🚫 Ο χρήστης δεν είναι συνδεδεμένος!");
        }

        UserDomain user = (UserDomain) authentication.getPrincipal();

        // Αν ο χρήστης έχει ήδη επιλεγμένη θέση, δεν του επιτρέπουμε άλλη
        if (user.getSelectedSpot() != null) {
            return ResponseEntity.badRequest().body("🚫 Έχετε ήδη επιλεγμένη θέση parking! Αποδεσμεύστε την πρώτα.");
        }

        ParkingSpot spot = parkingSpotRepository.findById(spotId)
                .orElseThrow(() -> new RuntimeException("🚫 Η θέση δεν βρέθηκε!"));

        if (!spot.isAvailable()) {
            return ResponseEntity.badRequest().body("🚫 Η θέση είναι ήδη κατειλημμένη.");
        }

        // ✅ Κρατάμε τη θέση για τον χρήστη
        spot.setAvailable(false);
        user.setSelectedSpot(spot);

        parkingSpotRepository.save(spot);
        userRepository.save(user);

        return ResponseEntity.ok("✅ Θέση " + spot.getSpotNumber() + " κρατήθηκε!");
    }


    // 🔹 4. Αποδέσμευση (ξεπαρκάρισμα) ParkingSpot
    @PostMapping("/release")
    public ResponseEntity<String> releaseSpot(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("🚫 Ο χρήστης δεν είναι συνδεδεμένος!");
        }

        UserDomain user = (UserDomain) authentication.getPrincipal();

        if (user.getSelectedSpot() == null) {
            return ResponseEntity.badRequest().body("⚠️ Δεν έχετε επιλεγμένη θέση για να αποδεσμεύσετε!");
        }

        ParkingSpot spot = user.getSelectedSpot();
        spot.setAvailable(true);
        user.setSelectedSpot(null);

        parkingSpotRepository.save(spot);
        userRepository.save(user);

        return ResponseEntity.ok("🔓 Η θέση " + spot.getSpotNumber() + " αποδεσμεύτηκε.");
    }

    // Νέο endpoint: Επιστρέφει μόνο τους αριθμούς των διαθέσιμων θέσεων για μια συγκεκριμένη ζώνη
    @GetMapping("/zone/{zoneId}/availableNumbers")
    public ResponseEntity<List<Integer>> getAvailableSpotNumbers(@PathVariable Long zoneId) {
        List<ParkingSpot> availableSpots = parkingSpotRepository.findAvailableSpotsByZoneId(zoneId);

        if (availableSpots.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList()); // ✅ Επιστροφή κενής λίστας αν δεν υπάρχουν διαθέσιμες θέσεις
        }

        List<Integer> spotNumbers = availableSpots.stream()
                .map(ParkingSpot::getSpotNumber)
                .collect(Collectors.toList());

        return ResponseEntity.ok(spotNumbers);
    }
}
