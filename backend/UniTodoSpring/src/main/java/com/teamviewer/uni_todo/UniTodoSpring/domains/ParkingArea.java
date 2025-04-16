package com.teamviewer.uni_todo.UniTodoSpring.domains;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "parking_area")
public class ParkingArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "area_name", nullable = false, unique = true)
    private String areaName;

    @Column(name = "description")
    private String description;

    // Ένα ParkingArea έχει πολλές ζώνες (ParkingZone)
    @OneToMany(mappedBy = "parkingArea", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkingZone> parkingZones;

    public ParkingArea() {}

    public ParkingArea(String areaName, String description) {
        this.areaName = areaName;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ParkingZone> getParkingZones() {
        return parkingZones;
    }

    public void setParkingZones(List<ParkingZone> parkingZones) {
        this.parkingZones = parkingZones;
    }
}
