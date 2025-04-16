package com.teamviewer.uni_todo.UniTodoSpring.domains;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
public class UserDomain implements UserDetails {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Enumerated(EnumType.STRING)
    @Column
    private Role role;


    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column
    private String password;


    @OneToOne
    @JoinColumn(name = "selected_spot_id", unique = true)
    private ParkingSpot selectedSpot;

    public UserDomain(String username,String email,String password,Role role){
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserDomain(){

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public ParkingSpot getSelectedSpot() {
        return selectedSpot;
    }

    public void setSelectedSpot(ParkingSpot selectedSpot) {
        this.selectedSpot = selectedSpot;
    }
}
