package org.jsp.shoppingcartapi.dao;

import java.util.List;
import java.util.Optional;

import org.jsp.shoppingcartapi.dto.Product;
import org.jsp.shoppingcartapi.repository.ProductRepo;
import org.jsp.shoppingcartapi.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {
	@Autowired
	private ProductRepo repo;
	@Autowired
	public UserRepo userRepo;

	public Product saveProduct(Product product) {
		return repo.save(product);
	}

	public Product updateProduct(Product product) {
		return repo.save(product);
	}

	public Optional<Product> findById(int id) {
		return repo.findById(id);
	}

	public void deleteProduct(Integer id) {
		repo.deleteById(id);
	}

	public List<Product> findProductByMerchantId(int merchant_id) {
		return repo.findProductsByMerchantId(merchant_id);
	}

	public List<Product> findProductsInCart(int id) {
		return userRepo.findProductsInCart(id);
	}

	public List<Product> findProductInWishList(int id) {
		return userRepo.findProductInWishList(id);
	}
}
