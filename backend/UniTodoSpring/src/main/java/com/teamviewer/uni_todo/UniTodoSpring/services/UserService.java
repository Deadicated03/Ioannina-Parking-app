package com.teamviewer.uni_todo.UniTodoSpring.services;

import com.teamviewer.uni_todo.UniTodoSpring.domains.UserDomain;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public void saveUser(UserDomain user);
    public boolean isUserPresent(UserDomain user);
}
