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
import domain.POItem;
import service.BookService;
import service.OrderService;

@Controller
public class AnalyticsController {
	
	@Autowired
	BookService bookService;
	
	@Autowired
	OrderService orderService;
	
		
	@GetMapping("/monthlyReport")
	public String monthlyReport(@RequestParam(name="month", required=true)String month, @RequestParam(name="year", required=true)String year, Model model, HttpSession session) {
		Map<Book, Integer> books = new HashMap<Book, Integer>();
		List<POItem> orderedItems = orderService.getPOItemByDate(month, year);
		for (POItem item: orderedItems) {
			books.put(bookService.findById(item.getBid()), item.getQuantity());
			
		}
		/*for (Map.Entry<Book, Integer> item : books.entrySet()) {
			item.getValue()
		}*/
		//get book information
		if (books.isEmpty()) {
			model.addAttribute("message", "No results found.");
		}
		model.addAttribute("bookReport", books);

		if (session.getAttribute("role").equals("Admin") )
			return "report"; // Verify the User is a Admin and return the reports page
		else 
			return "404"; // Otherwise not authorized to view page.
		
	}
	
	
	

}
