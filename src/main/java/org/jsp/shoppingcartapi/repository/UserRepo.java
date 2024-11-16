package org.jsp.shoppingcartapi.repository;

import java.util.List;
import java.util.Optional;

import org.jsp.shoppingcartapi.dto.Product;
import org.jsp.shoppingcartapi.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Integer> {
	@Query("select u from User u where u.token=?1")
	User findUserByToken(String token);

	@Query("select u from User u where u.email=?1")
	User findUserByEmail(String email);

	@Query("select u from User u where u.email=?1 and password=?2")
	public Optional<User> loginVerifyByUser(String email, String password);

	@Query("select u.cart from User u where u.id=?1")
	public List<Product> findProductsInCart(int id);

	@Query("select u.wishList from User u where u.id=?1")
	public List<Product> findProductInWishList(int id);
}
