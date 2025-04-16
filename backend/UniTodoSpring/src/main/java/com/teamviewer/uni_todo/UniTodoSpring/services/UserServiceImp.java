package com.teamviewer.uni_todo.UniTodoSpring.services;

import com.teamviewer.uni_todo.UniTodoSpring.domains.Role;
import com.teamviewer.uni_todo.UniTodoSpring.domains.UserDomain;
import com.teamviewer.uni_todo.UniTodoSpring.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImp implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Προσθήκη BCryptPasswordEncoder

    @Override
    @Transactional
    public void saveUser(UserDomain user) {
        userRepository.save(user);
    }

    @Override
    public boolean isUserPresent(UserDomain user) {
        Optional<UserDomain> existingUser = userRepository.findByUsername(user.getUsername());
        return existingUser.isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDomain user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(
                        String.format("USER_NOT_FOUND %s", username)
                ));

        return user; // ✅ Τώρα το `user` είναι `UserDetails` λόγω `implements UserDetails`
    }

    // 🛠 Προσθήκη Admin χρήστη αν δεν υπάρχει
    @PostConstruct
    public void initAdminUser() {
        Optional<UserDomain> adminUser = userRepository.findByUsername("admin");
        if (adminUser.isEmpty()) {
            UserDomain admin = new UserDomain(
                    "admin",
                    "admin@example.com",
                    passwordEncoder.encode("admin123"), // Ασφαλής αποθήκευση κωδικού
                    Role.ADMIN
            );
            userRepository.save(admin);
            System.out.println("✅ Admin user created successfully!");

        }
    }
}
