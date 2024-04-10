package lt.codeacademy.learn.securitydb.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import lt.codeacademy.learn.securitydb.entities.MyUser;
import lt.codeacademy.learn.securitydb.exceptions.UserAlreadyExistException;
import lt.codeacademy.learn.securitydb.services.MyUserDetailsService;

@Controller
public class MainController {
	Logger logger = LoggerFactory.getLogger(MainController.class);
	@Autowired
	MyUserDetailsService uService;
	
	@GetMapping("/")
	public String getIndex() {
		return "index";
	}
	
	@GetMapping("/user/hello")
	public String helloUser() {
		return "/user/hello";
	}
	
	@GetMapping("/admin/users")
	public String adminUsers(Model model) {
		model.addAttribute("users",uService.loadAllUsers() ); 		
		return "/admin/users";
	}
	
	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}
	
	@GetMapping("/registration")
	public String showRegistrationForm(WebRequest request, Model model) {
	    MyUser userDto = new MyUser();	    
	    model.addAttribute("user", userDto);
	    return "registration";
	}
	
	@PostMapping("/registration")
	public String registerUserAccount(MyUser user, Model model) {
		user.setRole("ADMIN");		
		logger.info("Registering new user: " + user);
		try {
			MyUser registered = uService.registerNewUserAccount(user);
			return "redirect:/";
		} catch (UserAlreadyExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("user", user);
		//model.addAttribute(errors);
		return "registration";
	}
	
	@GetMapping("/json")
	public @ResponseBody MyUser getJson() {
		return new MyUser();
	}
}
