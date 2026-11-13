package com.ecom_application.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "id", "street", "city", "state", "country", "pincode" })
public class AddressDTO {
	private Integer id;
	private String street;
	private String city;
	private String state;
	private String country;
	private String pincode;

}
