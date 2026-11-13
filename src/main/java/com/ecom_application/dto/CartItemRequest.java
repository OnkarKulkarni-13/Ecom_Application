package com.ecom_application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemRequest {
	private Integer productId;
	private Integer quantity;

}
