package ctrl;

import java.text.DecimalFormat;
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
import domain.Cart;
import domain.Checkout;
import domain.Login;
import domain.Registration;
import domain.User;
import service.BookService;
import service.UserService;

/**
 * Handles any User requests, Login/Register, directing and redirecting
 *
 */
@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	BookService bookService;
	
	private Map<String, Cart> cart;
	private List<String> cardTypes = Arrays.asList("Visa", "Mastercard");
	private List<String> provinces = Arrays.asList("ON", "QC", "NS", "NB", "MB", "BC", "PE", "SK", "AB", "NL");
	private List<String> roles = Arrays.asList("Admin", "Customer", "Partner");
	private List<String> countries = Arrays.asList("Canada");
	
	
	/** Returns the Administrator view only if the User logged in is an Administrator
	 * @author Anil
	 * @return report.jspx
	 */
	@GetMapping("/report")
	public String adminPage(HttpSession session, Model model) {
		if (session.getAttribute("role") != null) {
			if (session.getAttribute("role").equals("Admin")) {
				model.addAttribute("reportStyle", "reportStyle");
				return "report"; // Verify the User is a Admin and return the reports page
			}
			else 
				return "404"; // Otherwise not authorized to view page.
		} else {
			return "404";
		}
	
	}
	
	/** Returns the Partner view only if the User logged in is a Partner
	 * @author Anil
	 * @return orders.jspx
	 */
	@GetMapping("/orders")
	public String orderPage(HttpSession session, Model model) {
		if (session.getAttribute("role") != null) {
			if (session.getAttribute("role").equals("Partner")) {
				model.addAttribute("orderStyle", "orderStyle");
				return "orders";
			}
			else 
				return "404";
		} else {
			return "404";
		}
		
	}
	
	/**
	 * Returns the homepage (index) upon request. Appropriate view is presented depending on role.
	 * @author Anil
	 * @return homepage view
	 */
	@RequestMapping(value= {"/", "/index"})
	public String getHomePage(Model model, HttpSession session) {
		if (session.getAttribute("role") == null) {
			// Visitor View
			model.addAttribute("books", bookService.findAll());
			model.addAttribute("bookStyle", "bookStyle");
			return "books";
		}  else if (session.getAttribute("role").equals("Partner")) {
			// Partner View
			model.addAttribute("orderStyle", "orderStyle");
			return "orders";
		} else if (session.getAttribute("role").equals("Admin")) {
			// Admin View
			model.addAttribute("reportStyle", "reportStyle");
			return "report";
		} else {
			model.addAttribute("books", bookService.findAll());
			model.addAttribute("bookStyle", "bookStyle");
			return "books";
		}
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
		model.addAttribute("loginStyle", "loginStyle");
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
		String page = null;
		double singlePrice = 0.0;
		double totalPrice = 0.0;
		DecimalFormat df = new DecimalFormat("#.##");
		if (userid == null) {
			// User is a Visitor
			// Redirect to Login Page.
			Login login = new Login();
			page = "login";
			model.addAttribute("loginStyle", "loginStyle");
			model.addAttribute("login", login);
		} else {
			//Verify the User is a Customer
			if (session.getAttribute("role").equals("Customer")) {
				Checkout checkout = new Checkout();
				model.addAttribute("checkout", checkout);
				model.addAttribute("checkoutStyle", "checkoutStyle");
				model.addAttribute("provinces", provinces);	// List of Provinces for <form:select>
				model.addAttribute("cardTypes", cardTypes); // List of Card Types for <form:select>
				model.addAttribute("countries", countries); // List of Countries for <form:select>
				
			}
			page = "checkout";
		}
		// Logged In, Show Checkout
		// Update the cart.
		if (session.getAttribute("cart") != null) {
			cart = (Map<String, Cart>) session.getAttribute("cart");
			for (Map.Entry<String, Cart> cartItem : cart.entrySet()) {
				singlePrice = cartItem.getValue().getBook().getPrice() * cartItem.getValue().getQuantity();
				totalPrice = totalPrice + singlePrice;
				}
		}
		session.setAttribute("totalPrice", df.format(totalPrice));
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
		double singlePrice = 0.0;
		double totalPrice = 0.0;
		DecimalFormat df = new DecimalFormat("#.##");
		User loggedInUser = userService.validateUser(login); // return the User in Database, based on login credentials.
		
		System.out.println(loggedInUser);
		if (loggedInUser != null) {
			//LOGIN SUCCESS
			if (loggedInUser.getRole().equals(UserService.ROLE_ADMIN)) {
				addUserInSession(loggedInUser, session);
				page = "report";
				model.addAttribute("reportStyle", "reportStyle");
			}
			if (loggedInUser.getRole().equals(UserService.ROLE_PARTNER)) {
				addUserInSession(loggedInUser, session);
				page = "orders";
				model.addAttribute("orderStyle", "orderStyle");
			}
			if (loggedInUser.getRole().equals(UserService.ROLE_USER)) {
				// User is a Customer, redirect to homepage.
				addUserInSession(loggedInUser, session);
				if (session.getAttribute("cart") != null) {
					cart = (Map<String, Cart>) session.getAttribute("cart");
					for (Map.Entry<String, Cart> cartItem : cart.entrySet()) {
						singlePrice = cartItem.getValue().getBook().getPrice() * cartItem.getValue().getQuantity();
						totalPrice = totalPrice + singlePrice;
						}
				}
				Checkout checkout = new Checkout();
				model.addAttribute("checkout", checkout);
				model.addAttribute("provinces", provinces);
				model.addAttribute("cardTypes", cardTypes);
				model.addAttribute("roles", roles);
				model.addAttribute("countries", countries);
				session.setAttribute("totalPrice", df.format(totalPrice));
				page = "checkout";
				model.addAttribute("checkoutStyle", "checkoutStyle");
			}
			mav = new ModelAndView(page, "model", model);
			return mav;
		} else {
			//LOGIN FAILED
			mav = new ModelAndView("login"); // Return the login page and indicate the credentials were incorrect.
			mav.addObject("message", "Username or Password is wrong!!");
			model.addAttribute("loginStyle", "loginStyle");
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
		model.addAttribute("regStyle", "regStyle");
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
			model.addAttribute("regStyle", "regStyle");
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
			model.addAttribute("regStyle", "regStyle");
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
		model.addAttribute("loginStyle", "loginStyle");
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
		model.addAttribute("checkoutStyle", "checkoutStyle");
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
		session.setAttribute("address", a.get(0));
		session.setAttribute("billing", b.get(0));
	}
}
