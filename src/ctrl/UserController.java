package ctrl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.Login;
import domain.User;
import service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/login")
	public String displayLogin(Model model) {
		Login login = new Login();
		model.addAttribute("login", login);
		return "checkout";
	}
	
	@PostMapping("/loginProcess")
	public ModelAndView processLogin(@Valid @ModelAttribute("login")Login login, Errors errors, Model model) {
		
		ModelAndView mav = null;
		User loggedInUser = userService.validateUser(login);
		
		if (loggedInUser != null) {
			//LOGIN SUCCESS
			
			
			
			return new ModelAndView("welcome", "firstname", loggedInUser.getFirstname());
		} else {
			//LOGIN FAILED
			mav = new ModelAndView("checkout");
			mav.addObject("message", "Username or Password is wrong!!");
		}
		return mav;
	}
	
	@GetMapping("/register")
	public String displayRegister(Model model) {
		User user = new User();
		List<String> cardTypes = Arrays.asList("Visa", "Mastercard", "American Express");
		List<String> provinces = Arrays.asList("ON", "QC", "NS", "NB", "MB", "BC", "PE", "SK", "AB", "NL");
		List<Integer> roles = Arrays.asList(1, 2);
		model.addAttribute("provinces", provinces);
		model.addAttribute("cardTypes", cardTypes);
		model.addAttribute("user", user);
		model.addAttribute("roles", roles);
		return "register";
	}
	
	@PostMapping("/registerProcess")
	public ModelAndView addUser(@Valid @ModelAttribute("user") User user, Errors errors, Model model) throws Exception {
		int result;
		if (errors.hasErrors()) {
			ModelAndView mav = new ModelAndView("register");
			List<String> cardTypes = Arrays.asList("Visa", "Mastercard", "American Express");
			List<String> provinces = Arrays.asList("ON", "QC", "NS", "NB", "MB", "BC", "PE", "SK", "AB", "NL");
			List<Integer> roles = Arrays.asList(1, 2);
			model.addAttribute("provinces", provinces);
			model.addAttribute("cardTypes", cardTypes);
			model.addAttribute("roles", roles);
			return mav;
		}
		result = userService.register(user);
		if (result == 1) {
			ModelAndView mav = new ModelAndView("register");
			mav.addObject("message", "Username already exists");
			return mav;
		}
		return new ModelAndView("welcome", "firstname", user.getFirstname());
	}
	
	@GetMapping("/user/dashboard")
	public String userDashboard() {
		return "user_dashboard";
	}
	
	@GetMapping("/admin/dashboard")
	public String adminDashboard() {
		return "admin_dashboard";
	}
}
