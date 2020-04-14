package ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import service.BookService;
import service.OrderService;

@Controller
public class AnalyticsController {
	
	@Autowired
	BookService bookService;
	
	@Autowired
	OrderService orderService;
	
	@GetMapping("/monthlyReport")
	public ModelAndView montlyReport(@RequestParam("month")String month, @RequestParam("year")String year) {
		ModelAndView mav = new ModelAndView("report");
		return mav;
		
	}
	
	
	

}
