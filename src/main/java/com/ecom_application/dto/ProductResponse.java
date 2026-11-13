package com.ecom_application.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

	private Integer id;

	private String name;
	private String description;
	private Double price;
	private Integer stockQuantity;
	private String catagory;
	private String imageUrl;
	private Boolean active;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
