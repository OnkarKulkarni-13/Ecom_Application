package com.ecom_application.dto;

import com.ecom_application.config.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private UserRole role = UserRole.CUSTOMER;
	private AddressDTO address;

}
