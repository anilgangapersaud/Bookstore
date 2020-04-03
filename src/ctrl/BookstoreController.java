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

import domain.Book;
import domain.Login;
import domain.PO;
import domain.User;
import service.BookService;
import service.OrderService;
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
	BookService bookService;
	
	@Autowired
	OrderService orderService;
	
	/**
	 * Given a unique bid, returns detailed information of that book.
	 * Browser Component
	 * @author Anil
	 * @param bid
	 * @return
	 */
	@GetMapping("/getProductInfo")
	public ModelAndView productCatalog(@RequestParam("bid") String bid) {
		ModelAndView mav = new ModelAndView("catalog");
		Book b = bookService.findById(bid);
		String title = b.getTitle();
		String author = b.getAuthor();
		String category = b.getCategory();
		String isbn = b.getBid();
		double price = b.getPrice();
		mav.addObject("title", title);
		mav.addObject("author", author);
		mav.addObject("category", category);
		mav.addObject("bid", isbn);
		mav.addObject("price", price);
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
		mav.addObject("orders", orders);
		return mav;
	}
	
}
