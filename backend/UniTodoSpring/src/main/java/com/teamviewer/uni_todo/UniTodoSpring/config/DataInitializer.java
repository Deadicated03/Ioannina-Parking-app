package com.teamviewer.uni_todo.UniTodoSpring.config;

import com.teamviewer.uni_todo.UniTodoSpring.domains.ParkingArea;
import com.teamviewer.uni_todo.UniTodoSpring.domains.ParkingZone;
import com.teamviewer.uni_todo.UniTodoSpring.domains.ParkingSpot;
import com.teamviewer.uni_todo.UniTodoSpring.repositories.ParkingAreaRepository;
import com.teamviewer.uni_todo.UniTodoSpring.repositories.ParkingZoneRepository;
import com.teamviewer.uni_todo.UniTodoSpring.repositories.ParkingSpotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ParkingAreaRepository parkingAreaRepository;
    private final ParkingZoneRepository parkingZoneRepository;
    private final ParkingSpotRepository parkingSpotRepository;

    public DataInitializer(ParkingAreaRepository parkingAreaRepository,
                           ParkingZoneRepository parkingZoneRepository,
                           ParkingSpotRepository parkingSpotRepository) {
        this.parkingAreaRepository = parkingAreaRepository;
        this.parkingZoneRepository = parkingZoneRepository;
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Override
    public void run(String... args) {
        if (parkingAreaRepository.count() == 0) {
            System.out.println(">>> [DataInitializer] Δημιουργία προκαθορισμένων Parking Areas...");

            // ✅ Δημιουργία μιας κύριας περιοχής στάθμευσης (π.χ. "Ιωάννινα")
            ParkingArea area = new ParkingArea("Ιωάννινα", "Κεντρική περιοχή στάθμευσης στην πόλη των Ιωαννίνων");
            parkingAreaRepository.save(area);

            // ✅ Δημιουργία μίας Ζώνης (π.χ. "Ζώνη 1") στην περιοχή "Ιωάννινα"
            ParkingZone zone = new ParkingZone("Ζώνη 1", area);
            parkingZoneRepository.save(zone);

            // ✅ Σταθερές συντεταγμένες από το Frontend για τις Θέσεις Parking
            double[][] parkingSpotsData = {
                    //p1z1
                    {39.673864, 20.860371}, // Parking Spot 1
                    {39.673824, 20.860493}, // Parking Spot 2
                    {39.673732, 20.860650}, // Parking Spot 3
                    {39.673629, 20.860760}, // Parking Spot 4
                    {39.673314, 20.861054}, // Parking Spot 5
                    {39.673190, 20.861179},  // Parking Spot 6


                    //p1z2
                    {39.669644, 20.862017},
                    {39.669948, 20.862680},
                    {39.670073, 20.863092},
                    {39.670296, 20.863539},
                    {39.670652, 20.863603},


                    //p2z1
                    {39.664673, 20.859231},  // Parking Spot 1
                    {39.664709, 20.859320},
                    {39.664722, 20.859377},
                    {39.664525, 20.859284},
                    {39.664549, 20.859374},
                    {39.664448, 20.859323},

                    //p1z1

            };

            // ✅ Δημιουργία θέσεων στάθμευσης με τις παραπάνω συντεταγμένες
            for (int i = 0; i < parkingSpotsData.length; i++) {
                double lat = parkingSpotsData[i][0];
                double lng = parkingSpotsData[i][1];

                ParkingSpot spot = new ParkingSpot(i + 1, lat, lng, true, zone);
                parkingSpotRepository.save(spot);
            }

            System.out.println(">>> [DataInitializer] Αρχικοποίηση ολοκληρώθηκε! Δημιουργήθηκαν:");
            System.out.println("    - 1 Parking Area (Ιωάννινα)");
            System.out.println("    - 1 Parking Zone (Ζώνη 1)");
            System.out.println("    - 6 Parking Spots με γεωγραφικές συντεταγμένες");
        } else {
            System.out.println(">>> [DataInitializer] Τα δεδομένα υπάρχουν ήδη. Δεν απαιτείται αρχικοποίηση.");
        }
    }
}
