package com.ecom_application.service;

import java.util.List;

import com.ecom_application.dto.AddressDTO;
import com.ecom_application.entity.Address;
import com.ecom_application.exception.ResourceNotFoundException;

public interface AddressService {
	public void createAddress(Address address);

	public List<AddressDTO> getAllAddress() throws ResourceNotFoundException;

	public AddressDTO getAddressById(Integer addressId) throws ResourceNotFoundException;

	public void updateAddressById(Address updatedAddress, Integer addressId) throws ResourceNotFoundException;

	public void deleteAddressById(Integer addressId) throws ResourceNotFoundException;

}
