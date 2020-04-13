package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.POItemDAO;
import dao.PoDAO;
import domain.PO;
import domain.POItem;

@Service
public class OrderServiceImpl implements OrderService {

	
	@Autowired
	public PoDAO podao;
	
	@Autowired
	public POItemDAO poItemDAO;
	
	
	@Override
	public void createOrder(PO p) {
		podao.save(p);
	}

	@Override
	public void updateOrder(PO p) {
		podao.update(p);
	}

	@Override
	public void deleteOrder(Integer poid) {
		podao.delete(poid);
	}

	@Override
	public PO findOrderById(Integer poid) {
		return podao.findById(poid);
	}

	@Override
	public List<PO> findAllOrders() {
		return podao.findAll();
	}

	@Override
	public List<PO> findOrderByProperty(String propName, Object propValue) {
		return podao.findByProperty(propName, propValue);
	}

	@Override
	public void createPOItem(POItem p) {
		poItemDAO.save(p);
	}

	@Override
	public void updatePOItem(POItem p) {
		poItemDAO.update(p);
	}

	@Override
	public void deletePOItem(Integer itemid) {
		poItemDAO.delete(itemid);
	}

	@Override
	public POItem findPOItemById(Integer itemid) {
		 return poItemDAO.findById(itemid);
	}

	@Override
	public List<POItem> findAllPOItems() {
		return poItemDAO.findAll();
	}

	@Override
	public List<POItem> findPOItemByProperty(String propName, Object propValue) {
		return poItemDAO.findByProperty(propName, propValue);
	}

	@Override
	public List<PO> getOrdersByBid(String bid) {
		return podao.getOrdersByBid(bid);
	}

}
