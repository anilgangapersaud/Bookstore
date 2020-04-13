package dao;

import java.util.List;

import domain.Billing;

/**
 * An interface to create, update, delete, read, from the Billing Table
 * implemented in BillingDAOImpl
 * @author Anil
 *
 */
public interface BillingDAO {
	public void save(Billing b);
	public void update(Billing b);
	public void delete(Integer cardid);
	public Billing findById(Integer id);
	public List<Billing> findAll();
	public List<Billing> findByProperty(String propName, Object propValue);
}
