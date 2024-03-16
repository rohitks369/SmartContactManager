package com.smartcontactmanager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontactmanager.entities.Contact;
import com.smartcontactmanager.entities.User;
import com.smartcontactmanager.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	// method for adding common data to response
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
		System.out.println(userName + " username");

		// get the data of the user using username(email)
		User user = this.userRepository.getUserByUserName(userName);

		System.out.println("USER " + user);

		model.addAttribute("user", user);
	}

	// home dashboard
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {

		return "normal/user_dashboard";
	}

	// open add form handler
	@GetMapping("/addcontact")
	public String openAddContactForm(Model model) {
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());

		return "normal/addcontactform";
	}

	// process contact
	@PostMapping("/process-contact")
	public String addContact(@ModelAttribute Contact contact, @RequestParam("image") MultipartFile mpFile,
			Principal principal,HttpSession httpSession) {

		Path destPath = null;
		String originalFilename = null;

		/** making image name unique */
		String currDateTime = (LocalDate.now() + "").replace(":", "-");
		try {

			String currentLogInUserDetail = principal.getName();
			User user = this.userRepository.getUserByUserName(currentLogInUserDetail);

			System.out.println(currentLogInUserDetail);

			// processing and uploading file
			if(mpFile.isEmpty()) {
				System.out.println("file is empty");
			//	throw new Exception("Image file must not selected..!!");
				originalFilename = "contact_profile.png";
			}else {
				originalFilename = currDateTime+"@"+mpFile.getOriginalFilename();
			}	
				/** retrieve current class-path resource folder relative path */
				 File savedFile = new ClassPathResource("/static/image").getFile();
			 
				 destPath = Paths.get(savedFile.getAbsolutePath()+File.separator+originalFilename);
				 System.out.println("Image path :"+destPath);
				 
				contact.setImageUrl(originalFilename);
				
			contact.setUser(user);
			user.getContacts().add(contact);

			// save the contact
			this.userRepository.save(user);
			System.out.println("addedd to database");
			httpSession.setAttribute("message", new helper.Message("Your Contact is added !! Add More", "success"));

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ERROR : " + e.getMessage());
			e.printStackTrace();
			httpSession.setAttribute("message", new helper.Message("Something went wrong!! try again", "danger"));
		}
		return "normal/addcontactform";
	}
	
	//show contacts handler
	@GetMapping("show-contacts")
	public String showContacts(Model m) {
		return "show_contacts";
	}
	

}
