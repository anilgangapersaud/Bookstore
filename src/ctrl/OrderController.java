package ctrl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
	
	private List<String> cardTypes = Arrays.asList("Visa", "Mastercard");
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
			model.addAttribute("checkoutStyle", "checkoutStyle");
			return mav;
		}
		if (cart == null) {
			// Cart is empty, return to the checkout and display error.
			mav = new ModelAndView("checkout");
			addCheckout(mav);
			mav.addObject("message", "Your cart is empty!");
			model.addAttribute("checkoutStyle", "checkoutStyle");
			return mav;
		}
	
		// Store Order Information if Guest, Update Information if Customer
		if (session.getAttribute("role") != null) {
			if (session.getAttribute("role").equals("Customer")) {
				// Customer checkout -> Update Address and Billing Info
				checkout.getAddress().setUserid((int) session.getAttribute("userId"));
				checkout.getBilling().setUserid((int) session.getAttribute("userId"));
				int aid = userService.findByPropertyAddress("userid", checkout.getAddress().getUserid()).get(0).getAddressid();
				int cid = userService.findByPropertyBilling("userid", checkout.getBilling().getUserid()).get(0).getCardid();
				checkout.getAddress().setAddressid(aid);
				checkout.getBilling().setCardid(cid);
				userService.updateAddress(checkout.getAddress());
				userService.updateBilling(checkout.getBilling());
				session.setAttribute("address", checkout.getAddress());
				session.setAttribute("billing", checkout.getBilling());
			}
		} else {
			// Guest Checkout -> Store Address and Billing info
			try {
				userService.createAddress(checkout.getAddress());
			} catch (DataIntegrityViolationException e) {
				// Invalid Input
				mav = new ModelAndView("checkout");
				addCheckout(mav);
				mav.addObject("message", "Data you entered in invalid!");
				model.addAttribute("checkoutStyle", "checkoutStyle");
				return mav;
			}
			
			try {
				userService.createBilling(checkout.getBilling());
			} catch (DataIntegrityViolationException e) {
				// Invalid Input
				mav = new ModelAndView("checkout");
				addCheckout(mav);
				mav.addObject("message", "Data you entered in invalid!");
				model.addAttribute("checkoutStyle", "checkoutStyle");
				return mav;
			}
			
			session.setAttribute("address", checkout.getAddress());
			session.setAttribute("billing", checkout.getBilling());
		}
	
		Address currentAddress = (Address) session.getAttribute("address");
		Billing currentBilling = (Billing) session.getAttribute("billing");
		
		if (authorization % 3 == 0) {
			// Authorization Denied
			PO purchaseOrder = new PO();	
			// Add Information to Purchase Order
			purchaseOrder.setAddressID(currentAddress.getAddressid());
			purchaseOrder.setCardID(currentBilling.getCardid());
			
			purchaseOrder.setOrderDate(date);
			purchaseOrder.setStatus("DENIED");
			// DENY the request and return the checkout View
			mav = new ModelAndView("checkout");
			addCheckout(mav);
			mav.addObject("message", "Credit Card Authorization Failed.");
			model.addAttribute("checkoutStyle", "checkoutStyle");
			// Store the PO
			orderService.createOrder(purchaseOrder);
		} else {
			// Confirm Order 
			PO purchaseOrder = new PO();
			// Add Information to Purchase Order
			purchaseOrder.setAddressID(currentAddress.getAddressid());
			purchaseOrder.setCardID(currentBilling.getCardid());
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
				mav.addObject("msg", "Order #" + purchaseOrder.getPurchaseID() +" Successfully Processed. Thanks for shopping with Livraria!");
				// Remove items from cart
				cart.clear();
				session.setAttribute("cart", cart);
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
	public ModelAndView orderProcess(@RequestParam("bid")String bid, Model model) {
		ModelAndView mav = new ModelAndView("orders");
		try {
			List<PO> orders = orderService.getOrdersByBid(bid);
			if (orders.isEmpty()) {
				// No orders linked to this bid
				mav.addObject("message", "No results found.");
			}
			for (PO po : orders) {
				po.setshipTo(userService.findById(po.getAddressID()));
				po.setbillTo(userService.findByBillingId(po.getCardID()));
			}
			
			mav.addObject("orders", orders);
			
		} catch (DataIntegrityViolationException e) {
			// Invalid Input
			mav.addObject("message", "Data you entered is invalid!");
			model.addAttribute("orderStyle", "orderStyle");
			return mav;
		}
		
		// Set the Address and Billing of each PO, to retrieve in view.

		model.addAttribute("orderStyle", "orderStyle");
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
