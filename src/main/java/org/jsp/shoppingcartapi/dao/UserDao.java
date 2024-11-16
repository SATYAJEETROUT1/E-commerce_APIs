package org.jsp.shoppingcartapi.dao;

import java.util.Optional;

import org.jsp.shoppingcartapi.dto.User;
import org.jsp.shoppingcartapi.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	@Autowired
	public UserRepo repo;

	public User saveUser(User user) {
		return repo.save(user);
	}

	public User updateUser(User user) {
		return repo.save(user);
	}

	public User verifyUser(String token) {
		return repo.findUserByToken(token);
	}

	public User findUserByEmail(String email) {
		return repo.findUserByEmail(email);
	}

	public Optional<User> loginVerifyUser(String email, String password) {
		return repo.loginVerifyByUser(email, password);
	}

	public Optional<User> findById(int id) {
		return repo.findById(id);
	}
}
