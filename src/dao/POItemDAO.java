package dao;

import java.util.List;

import domain.PO;
import domain.POItem;



public interface POItemDAO {
	public void save(POItem p);
	public void update(POItem p);
	public void delete(Integer itemid);
	public POItem findById(Integer itemid);
	public List<POItem> findAll();
	public List<POItem> findByProperty(String propName, Object propValue);
}
