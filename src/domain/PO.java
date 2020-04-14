package domain;

import java.util.List;

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
	private Address shipTo;
	private Billing billTo;
	private List<POItem> items;
	
	
	public List<POItem> getItems() {
		return items;
	}
	public void setItems(List<POItem> items) {
		this.items = items;
	}
	public Address getshipTo() {
		return shipTo;
	}
	public void setshipTo(Address shipTo) {
		this.shipTo = shipTo;
	}
	public Billing getbillTo() {
		return billTo;
	}
	public void setbillTo(Billing billTo) {
		this.billTo = billTo;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public Integer getAddressID() {
		return addressid;
	}
	public void setAddressID(Integer addressID) {
		this.addressid = addressID;
	}
	public Integer getCardID() {
		return cardid;
	}
	public void setCardID(Integer cardID) {
		this.cardid = cardID;
	}
	public Integer getPurchaseID() {
		return poid;
	}
	public void setPurchaseID(Integer purchaseID) {
		this.poid = purchaseID;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
