package com.teamviewer.uni_todo.UniTodoSpring.repositories;

import com.teamviewer.uni_todo.UniTodoSpring.domains.ParkingArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ParkingAreaRepository extends JpaRepository<ParkingArea, Long> {
    // Μπορείς να προσθέσεις custom queries εδώ, αν χρειαστεί.
}
