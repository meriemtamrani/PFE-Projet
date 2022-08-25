package com.HRPlus.space.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.HRPlus.space.entities.UserInformation;
import com.HRPlus.space.repositories.IUtilidateurRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
	
	@Autowired
	private IUtilidateurRepo userRepo;
	
	@Autowired
	  private PasswordEncoder passwordEncoder;
	
	
	public boolean updateUsername(String email, String username) {
		Optional<UserInformation> opt = this.userRepo.findByEmail(email);
		UserInformation user;
		if(opt.isPresent()) {
			user =  opt.get();
			user.setUsername(username);
			 this.userRepo.save(user);
			 return true;
		}
		return false;
	}

	
	public boolean updatePassword(String email, String oldPass, String newPass) {
		Optional<UserInformation> opt = this.userRepo.findByEmail(email);
		UserInformation user;
		if(opt.isPresent()) {
			user =  opt.get();
			
			if(passwordEncoder.matches(oldPass, user.getPassword())) {
				user.setPassword(passwordEncoder.encode(newPass));
				 this.userRepo.save(user);
				 return true;
			}
		
		}
		return false;
	}

}
