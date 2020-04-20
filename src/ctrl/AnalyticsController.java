package ctrl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Book;
import domain.Cart;
import domain.PO;
import domain.POItem;
import service.BookService;
import service.OrderService;

@Controller
public class AnalyticsController {
	
	@Autowired
	BookService bookService;
	
	@Autowired
	OrderService orderService;
	
	/**
	 * Generate report by month and year.
	 * @author Tram
	 * @param month
	 * @param year
	 * @param session
	 * @return
	 */	
	@GetMapping("/monthlyReport")
	public String monthlyReport(@RequestParam(name="month", required=true)String month, @RequestParam(name="year", required=true)String year, Model model, HttpSession session) {
		List<POItem> orderedItems = orderService.getPOItemByDate(month, year);
		for (POItem item: orderedItems) {
			item.setBook(bookService.findById(item.getBid()));
			item.setOrderDate(orderService.findOrderById(item.getItemId()).getOrderDate());
		}

		if (orderedItems.isEmpty()) {
			model.addAttribute("message", "No results found.");
		}
		model.addAttribute("orderedItems", orderedItems);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
	
	
		if (session.getAttribute("role") != null) {
			if (session.getAttribute("role").equals("Admin")) {
				return "report"; 
			}
			else 
				return "404"; 
		} else {
			return "404";
		}
		
	}
	/**
	 * Redirects the user to the top 10 books page
	 * @author josedelgado
	 * @return top_books.jspx
	 */
	@GetMapping("/topBooks")
	public String topBooks()
	{
		return "top_books";
	}

	
	

}
