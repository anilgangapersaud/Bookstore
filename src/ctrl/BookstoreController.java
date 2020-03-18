package ctrl;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dao.TestBookDAO;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookstoreController {

	@RequestMapping("/welcome")
		public ModelAndView helloWorld() {
			String message = "Welcome.....";
			return new ModelAndView("welcome", "helloMessage", message);
	}
	
	@RequestMapping("/greeting")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		try {
			TestBookDAO.main(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "greeting";
	}
	
	
	
}
