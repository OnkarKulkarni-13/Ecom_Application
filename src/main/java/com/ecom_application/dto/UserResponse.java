package com.ecom_application.dto;

import java.time.LocalDateTime;

import com.ecom_application.config.UserRole;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;


@Data
@JsonPropertyOrder({
    "id",
    "firstName",
    "lastName",
    "email",
    "phone",
    "role",
    "createdAt",
    "updatedAt",
    "address"
})
public class UserResponse {
	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private UserRole role;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private AddressDTO address;

}
