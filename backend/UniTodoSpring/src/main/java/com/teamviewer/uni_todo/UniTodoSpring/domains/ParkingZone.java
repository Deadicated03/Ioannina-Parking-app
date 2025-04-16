package com.teamviewer.uni_todo.UniTodoSpring.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "parking_zone")
public class ParkingZone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "zone_name", nullable = false)
    private String zoneName;

    // Κάθε ParkingZone ανήκει σε ένα ParkingArea
    @ManyToOne
    @JoinColumn(name = "parking_area_id", nullable = false)
    @JsonIgnore
    private ParkingArea parkingArea;

    // Κάθε ParkingZone έχει πολλά ParkingSpot
    @OneToMany(mappedBy = "parkingZone", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkingSpot> parkingSpots;

    public ParkingZone() {}

    public ParkingZone(String zoneName, ParkingArea parkingArea) {
        this.zoneName = zoneName;
        this.parkingArea = parkingArea;
    }

    public Long getId() {
        return id;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public ParkingArea getParkingArea() {
        return parkingArea;
    }

    public void setParkingArea(ParkingArea parkingArea) {
        this.parkingArea = parkingArea;
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public void setParkingSpots(List<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }
}




