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
import domain.User;
import service.BookService;
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
	
	@GetMapping("/book")
	public void testBooks() {
		
		System.out.println(bookService.findByProperty("category", "Fiction").toString());
	}
	
	
	
}
