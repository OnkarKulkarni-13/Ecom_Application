package com.ecom_application.service;

import java.util.List;

import com.ecom_application.dto.CartItemRequest;
import com.ecom_application.entity.CartItem;
import com.ecom_application.exception.ResourceNotFoundException;
import com.ecom_application.exception.UserNotFoundException;

public interface CartItemService {

	public void addToCart(Integer userId, CartItemRequest cartItem)
			throws ResourceNotFoundException, UserNotFoundException;

	public List<CartItem> getAllCarts();

	public void deleteCartItem(Integer cartItemId) throws ResourceNotFoundException;

	void updateCartItem(Integer cartItemId, CartItemRequest cartItemRequest) throws ResourceNotFoundException;

	public List<CartItem> findCartByUserId(Integer userId) throws UserNotFoundException, ResourceNotFoundException;

}
