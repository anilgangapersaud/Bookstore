package dao;

import java.util.List;

import domain.Billing;

public interface BillingDAO {
	public void save(Billing b);
	public void update(Billing b);
	public void delete(Integer cardid);
	public Billing findById(String id);
	public List<Billing> findAll();
	public List<Billing> findByProperty(String propName, Object propValue);
}
