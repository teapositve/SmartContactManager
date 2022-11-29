package com.smart.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.UserRepository;
import com.smart.entitites.Contact;
import com.smart.entitites.User;
import com.smart.helper.Message;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	//Method for adding common data to response
	@ModelAttribute
	public void addCommomData(Model m, Principal principal) {

		String username = principal.getName();

		System.out.println("USERNAME : " +username);

		//Get the user from Username(email)
		User user = this.userRepository.getUserByUserName(username);	

		//		System.out.println("User : "+user);

		m.addAttribute("User",user);	
		m.addAttribute("title", "User Dashboard");

	}

	//Dashboard Home Handler
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {


		return "normal/user_dashboard";
	}

	//Add Contact Form Handler\
	@RequestMapping("/add_contact")
	public String addContactForm(Model m) {

		m.addAttribute("title", "Add Contact");
		m.addAttribute("contact", new Contact());

		return "normal/add_contact_form";
	}

	//Process Form
	@PostMapping("/process_contact")
	public String processContact(
			@ModelAttribute Contact contact, 
			@RequestParam("profileImage") MultipartFile file, 
			Principal principal, HttpSession session) {

		try {

			String name = principal.getName();
			User user = this.userRepository.getUserByUserName(name);

			//Processing and Uploading imageFile
			if(file.isEmpty()) {
				System.out.println("File is Empty");
			}
			else {
				contact.setImage(file.getOriginalFilename()); //To get the ori. filename in Database
				File savefile = new ClassPathResource("static/img").getFile(); //Location file get store
				Path path = Paths.get(savefile.getAbsolutePath() + File.separator + file.getOriginalFilename()); //Path
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING); 
				System.out.println("Files is Uploaded Successfully");
			}

			System.out.println("UserName" +user);
			contact.setUser(user);
			user.getContacts().add(contact);
			this.userRepository.save(user);
			//		System.out.println("Data : "+contact);
			System.out.println("Contact Added Successfully");
			
			//Success Message
			session.setAttribute("message", new Message("Your Contact Added Successfully !!", "success"));
			
		}
			
		catch (Exception e) {
			System.out.println("Error : "+e.getMessage());
			//Error Message
			session.setAttribute("message", new Message("Something Went Wrong !!", "danger"));
		}
			

		return "normal/add_contact_form";
	}
}	


