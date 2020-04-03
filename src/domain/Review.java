package domain;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
public class Review {
	private String bid;
	private float rating;
	
	private String review;

	private String reviewer;
	public Review(String bid, int rating, String review, String reviewer) {
		super();
		this.bid = bid;
		this.rating = rating;
		this.review = review;
		this.reviewer = reviewer;
	}
	public Review()
	{
		
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getReviewer() {
		return reviewer;
	}
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	
	
}
