package ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import service.BookService;
import service.OrderService;

@Controller
public class AnalyticsController {
	
	@Autowired
	BookService bookService;
	
	@Autowired
	OrderService orderService;
	
	

}
