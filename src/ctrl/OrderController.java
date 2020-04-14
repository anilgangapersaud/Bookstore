package ctrl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Address;
import domain.Billing;
import domain.Cart;
import domain.Checkout;
import domain.PO;
import domain.POItem;
import domain.User;
import service.OrderService;
import service.UserService;

@Controller
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@Autowired
	UserService userService;
	
	private List<String> cardTypes = Arrays.asList("Visa", "Mastercard", "American Express");
	private List<String> provinces = Arrays.asList("ON", "QC", "NS", "NB", "MB", "BC", "PE", "SK", "AB", "NL");
	private List<String> roles = Arrays.asList("Admin", "Customer", "Partner");
	private List<String> countries = Arrays.asList("Canada");
	private static int authorization = 1; // Hardcoded to deny every third request
	private Map<String, Cart> cart;
	
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
		cart = (Map<String, Cart>) session.getAttribute("cart");
		ModelAndView mav;
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		if (errors.hasErrors()) {
			// Errors detected in form, return to the checkout and display error.
			mav = new ModelAndView("checkout");
			addCheckout(mav);
			mav.addObject("message", "Information entered is invalid");
			return mav;
		}
		if (cart == null) {
			// Cart is empty, return to the checkout and display error.
			mav = new ModelAndView("checkout");
			addCheckout(mav);
			mav.addObject("message", "Your cart is empty!");
			return mav;
		}
	
		// Store Order Information
		userService.createAddress(checkout.getAddress());
		userService.createBilling(checkout.getBilling());
		
		if (authorization % 3 == 0) {
			// Authorization Denied
			PO purchaseOrder = new PO();	
			// Add Information to Purchase Order
			purchaseOrder.setAddressID(checkout.getAddress().getAddressid());
			purchaseOrder.setCardID(checkout.getBilling().getCardid());
			purchaseOrder.setOrderDate(date);
			purchaseOrder.setStatus("DENIED");
			// DENY the request and return the checkout View
			mav = new ModelAndView("checkout");
			addCheckout(mav);
			mav.addObject("message", "Credit Card Authorization Failed.");
			// Store the PO
			orderService.createOrder(purchaseOrder);
		} else {
			// Confirm Order 
			PO purchaseOrder = new PO();
			// Add Information to Purchase Order
			purchaseOrder.setAddressID(checkout.getAddress().getAddressid());
			purchaseOrder.setCardID(checkout.getBilling().getCardid());
			purchaseOrder.setOrderDate(date);
			purchaseOrder.setStatus("PROCESSED");
			// Store the PO
			orderService.createOrder(purchaseOrder);
			// For each item in cart, create a POItem tuple.
			for (Map.Entry<String, Cart> cartItem : cart.entrySet()) {
				POItem item = new POItem();
				item.setItemId(purchaseOrder.getPurchaseID());
				item.setBid(cartItem.getValue().getBook().getBid());
				item.setPrice(cartItem.getValue().getBook().getPrice());
				item.setQuantity(cartItem.getValue().getQuantity());
				orderService.createPOItem(item);	
			}
				mav = new ModelAndView("success");
				mav.addObject("msg", "Order Successfully Completed. Thanks for shopping with Livraria!");
				// Remove items from cart
			}
			authorization++;
			return mav;
	}
	/**
	 * Given a unique bid, returns all orders containing this bid.
	 * Browser Component
	 * @author Anil
	 * @param bid
	 * @return
	 */
	@GetMapping("/getOrdersByPartNumber")
	public ModelAndView orderProcess(@RequestParam("bid")String bid) {
		ModelAndView mav = new ModelAndView("orders");
		List<PO> orders = orderService.getOrdersByBid(bid);
		// Set the Address and Billing of each PO, to retrieve in view.
		for (PO po : orders) {
			po.setshipTo(userService.findById(po.getAddressID()));
			po.setbillTo(userService.findByBillingId(po.getCardID()));
		}
		mav.addObject("orders", orders);
		if (orders.isEmpty()) {
			// No orders linked to this bid
			mav.addObject("message", "No results found.");
		}
		return mav;
	}
	
	/* Adds Checkout information to the Model
	 * @author Anil
	 * 
	 */
	private void addCheckout(ModelAndView mav) {
		mav.addObject("provinces", provinces);
		mav.addObject("cardTypes", cardTypes);
		mav.addObject("roles", roles);
		mav.addObject("countries", countries);
	}
}
