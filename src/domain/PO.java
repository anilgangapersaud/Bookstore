package domain;

/**
 * Domain object to represent an PO (purchase order) entity.
 *
 */
public class PO {

	private Integer poid;
	private String status;
	private String orderDate;
	private Integer addressid;
	private Integer cardid;
	private Address a;
	private Billing b;
	
	
	
	public Address getA() {
		return a;
	}
	public void setA(Address a) {
		this.a = a;
	}
	public Billing getB() {
		return b;
	}
	public void setB(Billing billing) {
		this.b = billing;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public Integer getAddressid() {
		return addressid;
	}
	public void setAddressid(Integer addressid) {
		this.addressid = addressid;
	}
	public Integer getCardid() {
		return cardid;
	}
	public void setCardid(Integer cardid) {
		this.cardid = cardid;
	}
	public Integer getId() {
		return poid;
	}
	public void setId(Integer id) {
		this.poid = id;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
