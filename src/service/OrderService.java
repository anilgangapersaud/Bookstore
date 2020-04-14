package service;

import java.util.List;

import domain.PO;
import domain.POItem;

public interface OrderService {

	public void createOrder(PO p);
	public void updateOrder(PO p);
	public void deleteOrder(Integer poid);
	public PO findOrderById(Integer poid);
	public List<PO> findAllOrders();
	public List<PO> findOrderByProperty(String propName, Object propValue);
	public void createPOItem(POItem p);
	public void updatePOItem(POItem p);
	public void deletePOItem(Integer itemid);
	public POItem findPOItemById(Integer itemid);
	public List<POItem> findAllPOItems();
	public List<POItem> findPOItemByProperty(String propName, Object propValue);
	public List<PO> getOrdersByBid(String bid);
	//public List<Book> getOrdersBymonth(String month, String year);
}
