package ctrl;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.Book;
import domain.Cart;
import domain.Checkout;
import domain.Login;
import domain.User;
import service.BookService;
import service.UserService;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {

	@Autowired
	BookService bookService;
	Map<String, Cart> cart;

	/**
	 * Display the shopping cart that is previously saved to session.
	 * @author Tram
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/cart")
	public String displayCart(HttpSession session) {
		double totalPrice = 0.0;
		double singlePrice = 0.0;

		DecimalFormat df = new DecimalFormat("#.##");
		if (session.getAttribute("cart") != null) {
			cart = (Map<String, Cart>) session.getAttribute("cart");
			for (Map.Entry<String, Cart> cartItem : cart.entrySet()) {
				singlePrice = cartItem.getValue().getBook().getPrice() * cartItem.getValue().getQuantity();
				totalPrice = totalPrice + singlePrice;
			}
			session.setAttribute("totalPrice", df.format(totalPrice));
		}
		session.setAttribute("cart", cart);
		return "cart";
	}

	/**
	 * Update the shopping cart and total bill when the quantity is changed.
	 * @author Tram
	 * @param request
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/updateCart")
	public String updateCart(HttpServletRequest request, HttpSession session) {
		cart = (Map<String, Cart>) session.getAttribute("cart");
		String[] quantity = request.getParameterValues("quantity");
		int i = 0;
		for (Map.Entry<String, Cart> cartItem : cart.entrySet()) {
			cartItem.getValue().setQuantity(Integer.parseInt(quantity[i]));
			i++;
		}
		session.setAttribute("cart", cart);
		return "redirect:/cart";
	}

	/**
	 * Update the shopping cart and total bill when the item is removed.
	 * @author Tram
	 * @param bid
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("removeItem/{bid}")
	public String removeItem(@PathVariable(value = "bid") String bid, HttpSession session) {
		cart = (Map<String, Cart>) session.getAttribute("cart");
		String bookId = isExisting(bid, session);
		cart.remove(bookId);
		session.setAttribute("cart", cart);
		return "redirect:/cart";
	}

	/**
	 * Check if the item exists in the shopping cart.
	 * @author Tram
	 * @param bid
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String isExisting(String bid, HttpSession session) {
		// cart: bid, Cart(quantity, book)
		cart = (Map<String, Cart>) session.getAttribute("cart");
		for (int i = 0; i < cart.size(); i++) {
			if (cart.get(bid).getBook().getBid().equals(bid))
				return bid;
		}
		return "false";
	}

}
