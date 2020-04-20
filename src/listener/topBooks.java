package listener;


import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;


/**
 * Application Lifecycle Listener implementation class topBooks
 *
 */
@WebListener
public class topBooks implements HttpSessionAttributeListener {


    /**
     * Default constructor. 
     * 
     */
    public topBooks() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent se)  { 
         // TODO Auto-generated method stub
    	handleEvent(se);
    	
    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent se)  { 
         // TODO Auto-generated method stub
    	handleEvent(se);
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent se)  { 
         // TODO Auto-generated method stub
    	handleEvent(se);
    }
    void handleEvent(HttpSessionBindingEvent event) {
    	if ("topBooks".contentEquals(event.getName()))
    	{
    		// Add it to the web context so everybody can see it
    		event.getSession().getServletContext().setAttribute("displayTop", event.getSession().getAttribute("topBooks"));	
    	}
    	
    }
	
}
