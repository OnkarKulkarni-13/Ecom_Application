package com.ecom_application.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecom_application.dto.AddressDTO;
import com.ecom_application.entity.Address;
import com.ecom_application.entity.User;
import com.ecom_application.exception.ResourceNotFoundException;
import com.ecom_application.repository.AddressRepository;
import com.ecom_application.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	AddressRepository addressRepo;

	public AddressServiceImpl(AddressRepository addressRepo) {
		super();
		this.addressRepo = addressRepo;
	}

	public void createAddress(Address address) {
		addressRepo.save(address);
	}

	public List<AddressDTO> getAllAddress() throws ResourceNotFoundException {
		List<Address> allAddress = addressRepo.findAll();
		if (allAddress.isEmpty()) {
			throw new ResourceNotFoundException("Address Records are empty");
		}

		List<AddressDTO> listAddress = new ArrayList<>();

		for (Address address : allAddress) {
			listAddress.add(mapAddressToAddressResponse(address));

		}
		return listAddress;

	}

	public AddressDTO getAddressById(Integer addressId) throws ResourceNotFoundException {
		Address address = addressRepo.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "AddressId", addressId));
		return mapAddressToAddressResponse(address);
	}

	public void updateAddressById(Address updatedAddress, Integer addressId) throws ResourceNotFoundException {
		Address currentAddress = addressRepo.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "AddressId", addressId));
		currentAddress.setCity(updatedAddress.getCity());
		currentAddress.setCountry(updatedAddress.getCountry());
		currentAddress.setPincode(updatedAddress.getPincode());
		currentAddress.setState(updatedAddress.getState());
		currentAddress.setStreet(updatedAddress.getStreet());

		addressRepo.save(currentAddress);
	}

	public void deleteAddressById(Integer addressId) throws ResourceNotFoundException {
		Address currentAddress = addressRepo.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "AddressId", addressId));

		User user = currentAddress.getUser();
		if (user != null) {
			user.setAddress(null);
		}

		addressRepo.deleteById(addressId);
	}

	public AddressDTO mapAddressToAddressResponse(Address address) {
		AddressDTO addressResponse = new AddressDTO();
		addressResponse.setId(address.getId());
		addressResponse.setCity(address.getCity());
		addressResponse.setCountry(address.getCountry());
		addressResponse.setPincode(address.getPincode());
		addressResponse.setState(address.getState());
		addressResponse.setStreet(address.getStreet());

		return addressResponse;
	}

}
