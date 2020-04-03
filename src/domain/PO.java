package domain;

/**
 * Domain object to represent an PO (purchase order) entity.
 *
 */
public class PO {

	private Integer poid;
	private String lastname;
	private String firstname;
	private String status;
	private Integer addressid;
	
	public Integer getId() {
		return poid;
	}
	public void setId(Integer id) {
		this.poid = id;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getAddressid() {
		return addressid;
	}
	public void setAddressid(Integer addressid) {
		this.addressid = addressid;
	}
}
