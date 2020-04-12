package ctrl;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.Book;
import domain.Login;
import domain.PO;
import domain.Registration;
import domain.Review;
import domain.User;
import domain.Cart;
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
	

	
	Map<String, Cart> cart;
	/**
	 * Returns all the books available in the bookstore.
	 * @param model
	 * @author josedelgado
	 * @return
	 */
	@GetMapping("/books")
	public String showBooks(Model model)
	{
		model.addAttribute("books", bookService.findAll());
		return "books";
	}

	
	/**
	 * Filters the books by category (Fiction, Engineering, and Science)
	 * @param n: the category of the book
	 * @param model
	 * @author josedelgado
	 * @return
	 */
	  @GetMapping("/category") 
	  public String findByCategory(@RequestParam(name="bookCategory", required=true) String
	  n,Model model) 
	  { 
		  model.addAttribute("books",bookService.findByProperty("category",n )); 
		  return "books";
	  }
	  
	  /**
	   * Returns the requested book given a unique bid passed through the query string. 
	   * It also returns the reviews made by other users of the book returned.
	   * @param bid: Book ID
	   * @param model
	   * @author josedelgado
	   * @return
	   */
	  @GetMapping("/findId") 
	  public String findById(@RequestParam(name="bookId", required=true) String
	  bid,Model model) 
	  { 
		  List<Book> bookList= bookService.findByProperty("bid",bid );
		  List<Review> reviewList = bookService.findAllReviews(bid);
		  addInformationBook(bookList.get(0),model,reviewList);
		  return "book_detail";
	  }	 
	  /** 
	   * Adds a review of a book. It returns the updated list of reviews and
	   * the updated information of a book.
	   * @param r: Review made by a customer.
	   * @param bid
	   * @param errors
	   * @param model
	   * @param session
	   * @author josedelgado
	   * @return
	   * @throws Exception
	   */
	  @PostMapping("/addReview")
	  public String insertReview(@Valid @ModelAttribute("reviewModel") Review r,
			  @RequestParam(name="bookId", required=true) String bid,
			  Errors errors, Model model, HttpSession session) throws Exception
	  {
		  r.setBid(bid);
		  bookService.save(r);
		  List<Book> bookList= bookService.findByProperty("bid",r.getBid() );
		  List<Review> reviewList = bookService.findAllReviews(bid);
		  addInformationBook(bookList.get(0),model,reviewList);
		  return "book_detail";
	  }
	 
	  
	 
	/**
	 * Adds a book to the current session cart given a unique bid passed through the query string.
	 * @param bid
	 * @param session
	 * @param model
	 * @author josedelgado
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/addToCart")
	  public String addToCart(@RequestParam(name="buttonCart", required=true) String
			  bid,HttpSession session,Model model )
	  {
		  List<Review> reviewList = bookService.findAllReviews(bid);
		  List<Book> bookList= bookService.findByProperty("bid",bid );
		  Book b = bookList.get(0);
		  addInformationBook(  b, model, reviewList);
		  if( session.getAttribute("cart") != null)
		  {
			  Cart temporaryCart = new Cart();
			  cart = (Map<String, Cart>) session.getAttribute("cart");
			  // Adds 1 more to the quantity of a specific book already in the cart
			  if(cart.containsKey(b.getBid()))
			  {
				  temporaryCart = cart.get(b.getBid());
				  temporaryCart.setQuantity(bookService.increaseQuantity(temporaryCart.getQuantity()));
				  /*int quantity = temporaryCart.getQuantity();
				  cart.get(b.getBid()).setQuantity(bookService.increaseQuantity(quantity));*/
			  }
			  // makes the quantity of the specified book = 1 and add it to the map
			  else
			  {
				  temporaryCart.setQuantity(1);
				  cart.put(bid, temporaryCart);
				
			  }
			  temporaryCart = null;
			  
			 
		  }
		  // no cart session attribute.
		  else
		  {
			  cart = new HashMap<>();
			  cart.put(bid, new Cart(1,b));
			  
		  }
		  // Add Cart to session ( new or updates the old one).
		  session.setAttribute("cart", cart);
		  return "book_detail";
	  }
	
	/**b
	 * This method adds to the model all the information
	 * available for a Book (price, rating, etc). It is also used to update the details
	 * of a book for every requests since this can change at any time.
	 * @param book
	 * @param model
	 * @param reviewList
	 * @author josedelgado
	 */
	private void addInformationBook(Book book, Model model, List<Review> reviewList) {
	
		  DecimalFormat df = new DecimalFormat("0.00");
		  model.addAttribute("book", book);
		  model.addAttribute("Reviews",reviewList);
		  model.addAttribute("rating", df.format(bookService.findRatingAverage(reviewList)));
		  Review review = new Review();
		  model.addAttribute("reviewModel", review);
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
