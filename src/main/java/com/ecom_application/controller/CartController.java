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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ecom_application.dto.CartItemRequest;
import com.ecom_application.dto.ResponseDTO;
import com.ecom_application.entity.CartItem;
import com.ecom_application.exception.ResourceNotFoundException;
import com.ecom_application.exception.UserNotFoundException;
import com.ecom_application.service.CartItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CartController {

	private final CartItemService cartService;

	@PostMapping("/cart")
	public ResponseEntity<ResponseDTO> addToCart(@RequestHeader("X-User-ID") Integer userId,
			@RequestBody CartItemRequest cartItem) throws ResourceNotFoundException, UserNotFoundException {

		cartService.addToCart(userId, cartItem);

		return new ResponseEntity<>(new ResponseDTO("Cart created successfully!!", new Date(), true),
				HttpStatus.CREATED);
	}

	@GetMapping("/cart")
	public ResponseEntity<List<CartItem>> getAllCarts() {
		List<CartItem> allCarts = cartService.getAllCarts();

		return new ResponseEntity<>(allCarts, HttpStatus.OK);
	}

	@DeleteMapping("/deleteCartItem/{cartItemId}")
	public ResponseEntity<ResponseDTO> deleteCartItem(@PathVariable("cartItemId") Integer cartItemId)
			throws ResourceNotFoundException {

		cartService.deleteCartItem(cartItemId);

		return new ResponseEntity<ResponseDTO>(
				new ResponseDTO(String.format("CartItem with id : %d deleted successfully ", cartItemId), new Date(),
						true),
				HttpStatus.OK);
	}

	@PutMapping("/updateCartItem/{cartItemId}")
	public ResponseEntity<ResponseDTO> updateCartItem(@PathVariable("cartItemId") Integer cartItemId,
			@RequestBody CartItemRequest cartItemRequest) throws ResourceNotFoundException {
		cartService.updateCartItem(cartItemId, cartItemRequest);

		return new ResponseEntity<ResponseDTO>(
				new ResponseDTO(String.format("CartItem with id : %d updated successfully ", cartItemId), new Date(),
						true),
				HttpStatus.OK);

	}

	@GetMapping("/cartByUserId")
	public ResponseEntity<List<CartItem>> findCartByUserId(@RequestHeader("X-User-ID") Integer userId)
			throws UserNotFoundException, ResourceNotFoundException {
		List<CartItem> cartItemList = cartService.findCartByUserId(userId);

		return new ResponseEntity<List<CartItem>>(cartItemList, HttpStatus.OK);
	}

}
