package ctrl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import domain.Address;
import domain.Billing;
import domain.Login;
import domain.Registration;
import domain.User;
import service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	private List<String> cardTypes = Arrays.asList("Visa", "Mastercard", "American Express");
	private List<String> provinces = Arrays.asList("ON", "QC", "NS", "NB", "MB", "BC", "PE", "SK", "AB", "NL");
	private List<String> roles = Arrays.asList("Admin", "Customer", "Partner");
	private List<String> countries = Arrays.asList("Canada");
	
	
	@GetMapping("/")
	public ModelAndView getHomePage() {
		return new ModelAndView("index");
	}
	
	@GetMapping("/login")
	public String displayLogin(Model model) {
		Login login = new Login();
		model.addAttribute("login", login);
		return "checkout";
	}
	
	@PostMapping("/loginProcess")
	public ModelAndView processLogin(@Valid @ModelAttribute("login")Login login, Errors errors, Model model, HttpSession session) {
		
		ModelAndView mav = null;
		User loggedInUser = userService.validateUser(login);
		
		if (loggedInUser != null) {
			//LOGIN SUCCESS
			
			if (loggedInUser.getRole().equals(UserService.ROLE_ADMIN)) {
				addUserInSession(loggedInUser, session);
			}
			if (loggedInUser.getRole().equals(UserService.ROLE_PARTNER)) {
				addUserInSession(loggedInUser, session);
			}
			if (loggedInUser.getRole().equals(UserService.ROLE_USER)) {
				addUserInSession(loggedInUser, session);
			}
	
			return new ModelAndView("index", "firstname", loggedInUser.getFirstname());
		} else {
			//LOGIN FAILED
			mav = new ModelAndView("checkout");
			mav.addObject("message", "Username or Password is wrong!!");
		}
		return mav;
	}
	
	@GetMapping("/register")
	public String displayRegister(Model model) {
		Registration registration = new Registration();
		model.addAttribute("provinces", provinces);
		model.addAttribute("cardTypes", cardTypes);
		model.addAttribute("registration", registration);
		model.addAttribute("roles", roles);
		model.addAttribute("countries", countries);
		return "register";
	}
	
	@PostMapping("/registerProcess")
	public ModelAndView addUser(@Valid @ModelAttribute("registration") Registration registration, Errors errors, Model model) throws Exception {

		if (errors.hasErrors()) {
			ModelAndView mav = new ModelAndView("register");
			model.addAttribute("provinces", provinces);
			model.addAttribute("cardTypes", cardTypes);
			model.addAttribute("roles", roles);
			model.addAttribute("countries", countries);
			return mav;
		}
		
		int result = userService.register(registration.getRegistrationUser());
		
		if (result == 1) {
			ModelAndView mav = new ModelAndView("register");
			mav.addObject("message", "Username already exists");
			return mav;
		}
		
		int userId = registration.getRegistrationUser().getUserId();
		registration.getAddress().setUserid(userId);
		userService.createAddress(registration.getAddress());
		registration.getBilling().setUserid(userId);
		userService.createBilling(registration.getBilling());
		return new ModelAndView("welcome", "firstname", registration.getRegistrationUser().getFirstname());
	}
	
	@GetMapping("/logout") 
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}
	
	private void addUserInSession(User u, HttpSession session) {
		session.setAttribute("user", u);
		session.setAttribute("userId", u.getUserId());
		session.setAttribute("role", u.getRole());
		session.setAttribute("firstname", u.getFirstname());
	}
}
