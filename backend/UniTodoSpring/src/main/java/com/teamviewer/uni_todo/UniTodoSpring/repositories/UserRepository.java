package com.teamviewer.uni_todo.UniTodoSpring.repositories;

import com.teamviewer.uni_todo.UniTodoSpring.domains.UserDomain;
import com.teamviewer.uni_todo.UniTodoSpring.domains.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserDomain, Integer> {
    Optional<UserDomain> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    void deleteByRole(Role role);
}
