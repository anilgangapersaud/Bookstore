package ctrl;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.Checkout;
import service.OrderService;

@Controller
public class OrderController {

	@Autowired
	OrderService orderService;
	
	private List<String> cardTypes = Arrays.asList("Visa", "Mastercard", "American Express");
	private List<String> provinces = Arrays.asList("ON", "QC", "NS", "NB", "MB", "BC", "PE", "SK", "AB", "NL");
	private List<String> roles = Arrays.asList("Admin", "Customer", "Partner");
	private List<String> countries = Arrays.asList("Canada");
	
	/**
	 * Validates the checkout form for errors, and submits the order to Order Processing Service.
	 * @author Anil
	 * @param checkout
	 * @param errors
	 * @param model
	 * @param session
	 * @return
	 */
	@PostMapping("/checkoutProcess")
	public ModelAndView confirmPayment(@Valid @ModelAttribute("checkout") Checkout checkout, Errors errors, Model model, HttpSession session) {
		if (errors.hasErrors()) {
			ModelAndView mav = new ModelAndView("checkout");
			model.addAttribute("provinces", provinces);
			model.addAttribute("cardTypes", cardTypes);
			model.addAttribute("roles", roles);
			model.addAttribute("countries", countries);
			mav.addObject("message", "Information entered is invalid");
			return mav;
		}
		// TODO: Record transaction in PO table and deny every third request.
			ModelAndView mav = new ModelAndView("order");
			return mav;
	}
}
