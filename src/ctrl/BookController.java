package ctrl;


import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import domain.Book;
import service.BookService;
import service.OrderService;

/**
 * Handles REST Services, Exclusive for Partners.
 *
 */
@RestController
public class BookController {

	@Autowired
	BookService bookService;
	
	@Autowired
	OrderService orderService;
	
	/**
	 * Returns JSON information on a Book, given a unique bid passed through the query string.
	 * Checks if the session attribute 'role' is a Partner. This method is exclusive for partners.
	 * @param session
	 * @param bid
	 * @author Anil
	 * @return
	 */
	@RequestMapping("/getProductInfo")
	public Book getBooks(HttpSession session, @RequestParam("bid") String bid) {
		String role = (String) session.getAttribute("role"); //Get the Role from the Session
		if (role.equals("Partner")) {
			return bookService.findById(bid);
		} else {
			return null;
		}
	}
}
