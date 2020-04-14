package ctrl;


import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import domain.Address;
import domain.Billing;
import domain.Book;
import domain.PO;
import domain.POItem;
import domain.User;
import service.BookService;
import service.OrderService;
import service.UserService;

/**
 * Handles REST Services, Exclusive for Partners.
 *
 */
@RestController
public class RESTController {

	@Autowired
	BookService bookService;
	
	@Autowired 
	UserService userService;
	
	@Autowired
	OrderService orderService;
	
	/**
	 * Returns JSON information on a Book, given a unique bid passed through the query string.
	 * This method is exclusive for partners.
	 * @param session
	 * @param bid
	 * @author Anil
	 * @return
	 */
	@RequestMapping("/rest/getProductInfo")
	public Book getBooks(HttpSession session, @RequestParam("bid") String bid, @RequestParam("username") String username, @RequestParam("password") String password) {
		User u = userService.validateUser(username, password);
		if (u.getRole().equals("Partner")) {
			return bookService.findById(bid);
		} else {
			return null;
		}
	}
	
	/**
	 * Returns JSON information of a List of Purchase Orders, given a unique bid pass throught the query string.
	 *  This method is exclusive for partners.
	 * @param session
	 * @param bid
	 * @return
	 */
	@RequestMapping("/rest/getOrdersByPartNumber")
	public List<PO> getOrders(HttpSession session, @RequestParam("bid") String bid, @RequestParam("username") String username, @RequestParam("password") String password) {
		User u = userService.validateUser(username, password);
		if (u.getRole().equals("Partner")) {
			List<PO> orders = orderService.getOrdersByBid(bid);
			
			for (PO order : orders) {
				Address shipTo = userService.findById(order.getAddressID());
				Billing billTo = userService.findByBillingId(order.getCardID());
				order.setshipTo(shipTo);
				order.setbillTo(billTo);
				List<POItem> items = orderService.findPOItemByProperty("id", order.getPurchaseID());
				order.setItems(items);
			}
			
			return orders;
		} else 
			return null;
		}
	}

