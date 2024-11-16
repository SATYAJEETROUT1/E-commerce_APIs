package org.jsp.shoppingcartapi.service;

import java.util.List;
import java.util.Optional;

import org.jsp.shoppingcartapi.dao.MerchantDao;
import org.jsp.shoppingcartapi.dao.ProductDao;
import org.jsp.shoppingcartapi.dao.UserDao;
import org.jsp.shoppingcartapi.dto.MerchantDto;
import org.jsp.shoppingcartapi.dto.Product;
import org.jsp.shoppingcartapi.dto.ResponseStructure;
import org.jsp.shoppingcartapi.dto.User;
import org.jsp.shoppingcartapi.exception.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ProductService {
	@Autowired
	private ProductDao dao;
	@Autowired
	private MerchantDao merchantDao;
	@Autowired
	private UserDao userDao;

	public ResponseEntity<ResponseStructure<Product>> saveProduct(@RequestBody Product product, int mid) {
		ResponseStructure<Product> structure = new ResponseStructure<>();
		Optional<MerchantDto> recMerchant = merchantDao.findById(mid);
		if (recMerchant.isPresent()) {
			recMerchant.get().getProducts().add(product);
			product.setMerchant(recMerchant.get());
			dao.saveProduct(product);
			merchantDao.updateMerchant(recMerchant.get());
			structure.setData(product);
			structure.setStatusCode(HttpStatus.CREATED.value());
			structure.setMessage("Product added");
			return new ResponseEntity<ResponseStructure<Product>>(structure, HttpStatus.CREATED);
		}
		throw new IdNotFoundException();
	}

	public ResponseEntity<ResponseStructure<Product>> updateProduct(@RequestBody Product product, int mid) {
		ResponseStructure<Product> structure = new ResponseStructure<>();
		Optional<MerchantDto> recMerchant = merchantDao.findById(mid);
		if (recMerchant.isPresent()) {
			recMerchant.get().getProducts().add(product);
			product.setMerchant(recMerchant.get());
			structure.setData(dao.updateProduct(product));
			merchantDao.updateMerchant(recMerchant.get());
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			structure.setMessage("Product added");
			return new ResponseEntity<ResponseStructure<Product>>(structure, HttpStatus.ACCEPTED);
		}
		throw new IdNotFoundException();
	}

	public ResponseEntity<ResponseStructure<Product>> findById(int id) {
		Optional<Product> recProduct = dao.findById(id);
		ResponseStructure<Product> structure = new ResponseStructure<>();
		if (recProduct.isPresent()) {
			structure.setData(recProduct.get());
			structure.setMessage("Product Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Product>>(structure, HttpStatus.OK);
		}
		throw new IdNotFoundException();
	}

	public ResponseEntity<ResponseStructure<String>> deleteProduct(int id) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		Optional<Product> recProduct = dao.findById(id);
		if (recProduct.isPresent()) {
			dao.deleteProduct(id);
			structure.setMessage("Product deleted");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.OK);
		}
		throw new IdNotFoundException();
	}

	public ResponseEntity<ResponseStructure<List<Product>>> findProductsByMerchantId(int merchant_id) {
		ResponseStructure<List<Product>> strcture = new ResponseStructure<>();
		strcture.setData(dao.findProductByMerchantId(merchant_id));
		strcture.setStatusCode(HttpStatus.OK.value());
		strcture.setMessage("Products loaded");
		return new ResponseEntity<ResponseStructure<List<Product>>>(strcture, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<String>> addToCart(int product_id, int user_id) {
		Optional<User> recUser = userDao.findById(user_id);
		Optional<Product> recProduct = dao.findById(product_id);
		ResponseStructure<String> structure = new ResponseStructure<>();
		if (recUser.isPresent() && recProduct.isPresent()) {
			recUser.get().getCart().add(recProduct.get());
			userDao.updateUser(recUser.get());
			structure.setData("Product added to the cart");
			structure.setMessage("user and product found");
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.ACCEPTED);
		}
		throw new IdNotFoundException();
	}

	public ResponseEntity<ResponseStructure<String>> addToWishList(int product_id, int user_id) {
		Optional<User> recUser = userDao.findById(user_id);
		Optional<Product> recProduct = dao.findById(product_id);
		ResponseStructure<String> structure = new ResponseStructure<>();
		if (recUser.isPresent() && recProduct.isPresent()) {
			recUser.get().getCart().add(recProduct.get());
			userDao.updateUser(recUser.get());
			structure.setData("Product added to the WishList");
			structure.setMessage("user and product found");
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.ACCEPTED);
		}
		throw new IdNotFoundException();
	}

	public ResponseEntity<ResponseStructure<Product>> rateProduct(int product_id, int user_id, double rating) {
		Optional<User> recUser = userDao.findById(user_id);
		Optional<Product> recProduct = dao.findById(product_id);
		ResponseStructure<Product> structure = new ResponseStructure<>();
		if (recUser.isPresent() && recProduct.isPresent()) {
			Product p = recProduct.get();
			int n = p.getNo_of_users();
			double r = p.getRating() * n++;
			rating = (r + rating) / n;
			p.setNo_of_users(n);
			p.setRating(rating);
			dao.updateProduct(p);
			structure.setData(p);
			structure.setMessage("Product rated");
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			return new ResponseEntity<ResponseStructure<Product>>(structure, HttpStatus.ACCEPTED);
		}
		throw new IdNotFoundException();
	}

	public ResponseEntity<ResponseStructure<List<Product>>> findProductsInCart(int id) {
		ResponseStructure<List<Product>> structure = new ResponseStructure<>();
		structure.setData(dao.findProductsInCart(id));
		structure.setMessage("Following are the list of products in Cart");
		structure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<Product>>>(structure, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<List<Product>>> findProductInWishList(int id) {
		ResponseStructure<List<Product>> structure = new ResponseStructure<>();
		structure.setData(dao.findProductByMerchantId(id));
		structure.setMessage("Following are the list of products in Wishlist");
		structure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<Product>>>(structure, HttpStatus.OK);
	}

}
