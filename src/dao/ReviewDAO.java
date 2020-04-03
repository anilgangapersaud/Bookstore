package dao;

import java.util.List;


import domain.Review;



public interface ReviewDAO {
	/**
	 * Stores a new review in the database.
	 * @param a
	 * @author josedelgado
	 */
	public void save(Review a);
	/**
	 * Find all reviews of a book given its unique book ID.
	 * @param bid
	 * @author josedelgado
	 * @return List of reviews 
	 */
	public List<Review> findAllReviews(String bid);
}
