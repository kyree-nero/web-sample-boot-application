package com.example.app.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	public String getUsername() {
		
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
			User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return user.getUsername();
		}else {
			return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		}
	}
}
