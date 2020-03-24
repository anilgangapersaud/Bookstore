package domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Billing {

	private String cardType;
	
	private Integer userid;
	
	private Integer cardid;
	
	public Integer getCardid() {
		return cardid;
	}
	public void setCardid(Integer cardid) {
		this.cardid = cardid;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	@NotBlank(message="Enter the cardnumber.")
	@Size(max=19)
	private String cardNumber;
	
	@Pattern(regexp="^[0-1][0-9][0-9][0-9]$", message="Invalid Expiration Format")
	private String expDate;
	
	@NotBlank(message="Enter the cardholder name.")
	private String cardholderName;
	
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getCardholderName() {
		return cardholderName;
	}
	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}
}
