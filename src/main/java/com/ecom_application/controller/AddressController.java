package com.ecom_application.controller;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecom_application.dto.AddressDTO;
import com.ecom_application.dto.ResponseDTO;
import com.ecom_application.entity.Address;
import com.ecom_application.exception.ResourceNotFoundException;
import com.ecom_application.service.AddressService;

@RestController
public class AddressController {
	AddressService addressService;

	public AddressController(AddressService addressService) {
		super();
		this.addressService = addressService;
	}

	@PostMapping("/createAddress")
	public ResponseEntity<ResponseDTO> createAddress(@RequestBody Address address) {
		addressService.createAddress(address);
		return new ResponseEntity<>(new ResponseDTO("Address created successfully", new Date(), true),
				HttpStatus.ACCEPTED);
	}

	@GetMapping("/address")
	public ResponseEntity<List<AddressDTO>> getAllAddress() throws ResourceNotFoundException {
		List<AddressDTO> allAddress = addressService.getAllAddress();
		return new ResponseEntity<>(allAddress, HttpStatus.OK);

	}

	@GetMapping("/address/{id}")
	public ResponseEntity<AddressDTO> getAddressById(@PathVariable("id") Integer addressId)
			throws ResourceNotFoundException {
		AddressDTO addressById = addressService.getAddressById(addressId);

		return new ResponseEntity<>(addressById, HttpStatus.OK);

	}

	@PutMapping("/updateAddress/{id}")
	public ResponseEntity<ResponseDTO> updateAddressById(@RequestBody Address updatedAddress,
			@PathVariable("id") Integer addressId) throws ResourceNotFoundException {
		addressService.updateAddressById(updatedAddress, addressId);

		return new ResponseEntity<>(new ResponseDTO("Address Updated successfully", new Date(), true),
				HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/deleteAddress/{id}")
	public ResponseEntity<ResponseDTO> deleteAddressById(@PathVariable("id") Integer addressId)
			throws ResourceNotFoundException {
		addressService.deleteAddressById(addressId);

		return new ResponseEntity<>(
				new ResponseDTO("Address with Id: " + addressId + " deleted successfully", new Date(), true),
				HttpStatus.OK);

	}

}
