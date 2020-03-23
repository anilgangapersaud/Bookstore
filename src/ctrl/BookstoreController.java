package ctrl;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bean.Book;
import model.BookstoreModel;
import model.Login;
import model.User;
import service.UserService;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookstoreController {
	
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
		User user = userService.validateUser(login);
		
		if (user != null) {
			return new ModelAndView("welcome", "firstname", user.getFirstname());
		} else {
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
		model.addAttribute("provinces", provinces);
		model.addAttribute("cardTypes", cardTypes);
		model.addAttribute("user", user);
		return "register";
	}
	
	@PostMapping("/registerProcess")
	public ModelAndView addUser(@Valid @ModelAttribute("user") User user, Errors errors, Model model) throws Exception {
		int result;
		if (errors.hasErrors()) {
			ModelAndView mav = new ModelAndView("register");
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
}
