package org.jsp.shoppingcartapi.dao;

import java.util.List;
import java.util.Optional;

import org.jsp.shoppingcartapi.dto.Address;
import org.jsp.shoppingcartapi.repository.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDao {
	@Autowired
	private AddressRepo repo;

	public List<Address> findAddressByUserId(int id) {
		return repo.findAddressByUserId(id);
	}

	public Address saveAddress(Address address) {
		return repo.save(address);
	}

	public Address updateAddress(Address address) {
		return repo.save(address);
	}

	public Optional<Address> findById(int id) {
		return repo.findById(id);
	}

	public void deleteAddress(Integer id) {
		repo.deleteById(id);
	}
}
