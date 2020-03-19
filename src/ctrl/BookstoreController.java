package ctrl;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bean.Book;
import model.BookstoreModel;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookstoreController {
	
	@RequestMapping("/testdao")
	public void testdao(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) throws SQLException {
		BookstoreModel mainModel = new BookstoreModel();
		Map<String, Book> book = mainModel.retrieveAllBooks();
		for (Map.Entry<String, Book> entry : book.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Title = " + entry.getValue().getTitle());
		}
	}
	
}
