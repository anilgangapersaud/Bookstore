package dao;

import java.util.List;

import domain.Address;

public interface AddressDAO {
	public void save(Address a);
	public void update(Address a);
	public void delete(int addressid);
	public Address findById(int id);
	public List<Address> findAll();
	public List<Address> findByProperty(String propName, Object propValue);
}
