package ctrl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import domain.Address;
import domain.Billing;
import domain.Checkout;
import domain.Login;
import domain.Registration;
import domain.User;
import service.UserService;

/**
 * Handles any User requests, Login/Register, directing and redirecting
 *
 */
@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	private List<String> cardTypes = Arrays.asList("Visa", "Mastercard", "American Express");
	private List<String> provinces = Arrays.asList("ON", "QC", "NS", "NB", "MB", "BC", "PE", "SK", "AB", "NL");
	private List<String> roles = Arrays.asList("Admin", "Customer", "Partner");
	private List<String> countries = Arrays.asList("Canada");
	private int authorization = 1;
	
	/**
	 * Gets a request to redirect to the catalog page. Only available for Partners.
	 * @author Anil	
	 * @return catalog view if Partner, otherwise 404
	 */
	@GetMapping("/catalog")
	public String catalogPage(HttpSession session) {
		String role = (String) session.getAttribute("role");
		if (role != null) {
			if (role.equals("Partner")) 
				return "catalog";
		}
		return "catalog";
	}
	
	@GetMapping("/orders")
	public String orderPage() {
		return "orders";
	}
	
	/**
	 * Returns the homepage (index) upon request.
	 * @author Anil
	 * @return homepage view (index)
	 */
	@RequestMapping(value= {"/", "/index"})
	public ModelAndView getHomePage() {
		return new ModelAndView("index");
	}
	
	/**
	 * Gets a request for login page and attaches a Login object to retrieve data from Login form.
	 * @author Anil
	 * @param model
	 * @return login page
	 */
	@GetMapping("/login") 
	public String loginPage(Model model) {
		Login login = new Login();
		model.addAttribute("login", login);
		return "login";
	}
	
	/**
	 * Gets a request for the checkout page, goes to Login page if User not logged in, otherwise shows Checkout.
	 * @author Anil
	 * @param model
	 * @return checkout page
	 */
	@GetMapping("/checkout")
	public String displayLogin(Model model, HttpSession session) {
		Integer userid = (Integer) session.getAttribute("userId");
		String page;
		if (userid == null) {
			// Redirect to Login Page.
			Login login = new Login();
			page = "login";
			model.addAttribute("login", login);
		} else {
			// Logged In, Show Checkout
			Checkout checkout = new Checkout();
			page = "checkout";
			model.addAttribute("checkout", checkout);
			model.addAttribute("provinces", provinces);	// List of Provinces for <form:select>
			model.addAttribute("cardTypes", cardTypes); // List of Card Types for <form:select>
			model.addAttribute("roles", roles);			// List of Roles for <form:select>
			model.addAttribute("countries", countries); // List of Countries for <form:select>
		}
		return page;
	}
	
	/**
	 * Gets a POST request from a login form, and processes the login. Redirection appropriately.
	 * @author Anil
	 * @param login, contains login information from the form.
	 * @param errors, contains any errors from validation side of Login.
	 * @param model, used for redirection
	 * @param session, needed to add user logged in to the session.
	 * @return
	 */
	@PostMapping("/loginProcess")
	public ModelAndView processLogin(@Valid @ModelAttribute("login")Login login, Errors errors, Model model, HttpSession session) {
		ModelAndView mav = null;
		String page = null;
		User loggedInUser = userService.validateUser(login); // return the User in Database, based on login credentials.
		if (loggedInUser != null) {
			//LOGIN SUCCESS
			if (loggedInUser.getRole().equals(UserService.ROLE_ADMIN)) {
				addUserInSession(loggedInUser, session);
			}
			if (loggedInUser.getRole().equals(UserService.ROLE_PARTNER)) {
				addUserInSession(loggedInUser, session);
			}
			if (loggedInUser.getRole().equals(UserService.ROLE_USER)) {
				// User is a Customer, redirect to homepage.
				addUserInSession(loggedInUser, session);
				page = "checkout";
			}
//			Checkout checkout = new Checkout();
//			model.addAttribute("checkout", checkout);
//			model.addAttribute("provinces", provinces);
//			model.addAttribute("cardTypes", cardTypes);
//			model.addAttribute("roles", roles);
//			model.addAttribute("countries", countries);
			
			mav = new ModelAndView("index", "model", model);
			return mav;
		} else {
			//LOGIN FAILED
			mav = new ModelAndView("login"); // Return the login page and indicate the credentials were incorrect.
			mav.addObject("message", "Username or Password is wrong!!");
			return mav;
		}
	}
	
	/**
	 * Gets a request for Register page, attaches the Registration object to model collect information from the Registration form.
	 * @param model, contains Registration object.
	 * @return register page
	 * @author Anil
	 */
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
	
	
	/**
	 * Handles POST request from registration form.
	 * @param registration
	 * @param errors
	 * @param model
	 * @return
	 * @author Anil
	 * @throws DuplicateKeyException, if Username already exists in the database
	 */
	@PostMapping("/registerProcess")
	public ModelAndView addUser(@Valid @ModelAttribute("registration") Registration registration, Errors errors, Model model) throws DuplicateKeyException {
		if (errors.hasErrors()) {
			// Registration form has errors, return to the registration page.
			ModelAndView mav = new ModelAndView("register");
			model.addAttribute("provinces", provinces);
			model.addAttribute("cardTypes", cardTypes);
			model.addAttribute("roles", roles);
			model.addAttribute("countries", countries);
			mav.addObject("message", "Information entered is invalid");
			return mav;
		}
		
		try {
			 userService.register(registration.getRegistrationUser()); // Register the User in the database.
		} catch (Exception e) {
			// Username already exists in the database, return to the registration page.
			ModelAndView mav = new ModelAndView("register");
			model.addAttribute("provinces", provinces);
			model.addAttribute("cardTypes", cardTypes);
			model.addAttribute("roles", roles);
			model.addAttribute("countries", countries);
			mav.addObject("message", "Username already exists");
			return mav;
		}
		
		// Registration successful.
		ModelAndView mav = new ModelAndView("login"); // Redirect to the Login page.
		int userId = registration.getRegistrationUser().getUserId();
		registration.getAddress().setUserid(userId);
		userService.createAddress(registration.getAddress()); // Store the User's Address info in the address table.
		registration.getBilling().setUserid(userId);
		userService.createBilling(registration.getBilling()); // Store the User's Billing info in the Billing table.
		Login login = new Login(); 
		mav.addObject(login);
		mav.addObject("reg", "You have registered successfully, login with your credentials.");
		return mav;
	}
	
	/**
	 * Handles the logout request. Invalidates the session and notifies the user.
	 * @author Anil
	 * @param session
	 * @return
	 */
	@GetMapping("/logout") 
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:index?act=lo";
	}
	
	/**
	 * Gets the request that the User wants to checkout as a Guest(Visitor), redirects to the checkout page.
	 * @param model
	 * @return
	 */
	@GetMapping("visitorCheckout")
	public String showCheckout(Model model, HttpSession session) {
		Checkout checkout = new Checkout();
		model.addAttribute("checkout", checkout);
		model.addAttribute("provinces", provinces);
		model.addAttribute("cardTypes", cardTypes);
		model.addAttribute("roles", roles);
		model.addAttribute("countries", countries);
		return "checkout";
	}
	
	/**
	 * Adds the given User u passed as a parameter to the session scope. Also adds the User's Address and Billing info to the session scope as well.
	 * @author Anil
	 * @param u
	 * @param session
	 */
	private void addUserInSession(User u, HttpSession session) {
		session.setAttribute("user", u);
		session.setAttribute("userId", u.getUserId());
		session.setAttribute("role", u.getRole());
		session.setAttribute("firstname", u.getFirstname());
		List<Address> a = userService.findByPropertyAddress("USERID", u.getUserId());
		List<Billing> b = userService.findByPropertyBilling("USERID", u.getUserId());
		session.setAttribute("address", a);
		session.setAttribute("billing", b);
	}
}
