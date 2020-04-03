package dao;

import java.util.List;

import domain.Address;
import domain.PO;

/**
 * An interface to create, update, delete, read, from the PO Table
 * implemented in PoDAOImpl
 * @author Anil
 *
 */
public interface PoDAO {
	public void save(PO p);
	public void update(PO p);
	public void delete(Integer poid);
	public PO findById(Integer poid);
	public List<PO> findAll();
	public List<PO> findByProperty(String propName, Object propValue);
	public List<PO> getOrdersByBid(String bid);
}
