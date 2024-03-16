package com.smartcontactmanager.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontactmanager.entities.User;
import com.smartcontactmanager.repository.UserRepository;

import helper.Message;

@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home - Smart Contact Manager");
		return "home";
	}

	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About - Smart Contact Manager");
		return "about";
	}

	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title","Signup - Smart Contact Manager");
		model.addAttribute("user",new User());
		return "signup";
	}

	// handler for registering user
	@RequestMapping(value="/registerUser",method=RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult bindingResult,

			@RequestParam(value = "agreement",defaultValue="false") boolean agreement,
			Model model,HttpSession session) {
		
		try {
			if(bindingResult.hasErrors()) {
				System.out.println("Error "+bindingResult);
				model.addAttribute("user",user);
				return "signup";
			}
			
			if(!agreement) {
				System.out.println("You have not agreed the trems and conditions");
				throw new Exception("You have not agreed the trems and conditions");
			}
			
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("DEFAULT");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			System.out.println("Agreement "+agreement);
			
			User resultUser=this.userRepository.save(user);
			System.out.println("USER "+resultUser);
			model.addAttribute("user",new User());
			session.setAttribute("message", new Message("Sucessfully Registered  !! ", "alert-success"));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong !! "+e.getMessage(), "alert-danger"));
		}
		return "signup";
	}
	
	//handler for custom login
	@GetMapping("/signin")
	public String customLogin(Model model) {
		model.addAttribute("title", "Login - Smart Contact Manager");
		return "login";
	}
}
