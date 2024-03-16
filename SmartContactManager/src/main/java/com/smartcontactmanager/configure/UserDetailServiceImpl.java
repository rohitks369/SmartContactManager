package com.smartcontactmanager.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smartcontactmanager.entities.User;
import com.smartcontactmanager.repository.UserRepository;

public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//fetch user from database
		
		User user=userRepository.getUserByUserName(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("Could not found user !!");
		}
		CustomUserDetails customUserDetails=new CustomUserDetails(user);
		
		return customUserDetails;
	}

}
