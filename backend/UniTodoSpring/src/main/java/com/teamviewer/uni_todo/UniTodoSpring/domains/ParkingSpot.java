package com.teamviewer.uni_todo.UniTodoSpring.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "parking_spots") // Τυπικά ο πίνακας πρέπει να είναι στον πληθυντικό
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spot_number", nullable = false, unique = true)
    private int spotNumber;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable;

    // Κάθε ParkingSpot ανήκει σε ένα ParkingZone (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_zone_id", nullable = false)
    @JsonIgnore // Για να αποφύγουμε κυκλικές αναφορές στο JSON
    private ParkingZone parkingZone;

    // ------------------- Constructors -------------------

    public ParkingSpot() {}

    public ParkingSpot(int spotNumber, double latitude, double longitude, boolean isAvailable, ParkingZone parkingZone) {
        this.spotNumber = spotNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isAvailable = isAvailable;
        this.parkingZone = parkingZone;
    }

    // ------------------- Getters & Setters -------------------

    public Long getId() {
        return id;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(int spotNumber) {
        this.spotNumber = spotNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public ParkingZone getParkingZone() {
        return parkingZone;
    }

    public void setParkingZone(ParkingZone parkingZone) {
        this.parkingZone = parkingZone;
    }

    // ------------------- Επιπλέον Μέθοδοι -------------------

    /**
     * Επιστρέφει τις πληροφορίες της θέσης σε αναγνώσιμη μορφή.
     */
    public String getInfo() {
        return "Parking Spot #" + spotNumber + " [" + latitude + ", " + longitude + "]";
    }
}

