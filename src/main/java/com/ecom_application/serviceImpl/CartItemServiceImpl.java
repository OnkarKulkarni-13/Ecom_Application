package com.ecom_application.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecom_application.dto.CartItemRequest;
import com.ecom_application.dto.ProductResponse;
import com.ecom_application.entity.CartItem;
import com.ecom_application.entity.Product;
import com.ecom_application.entity.User;
import com.ecom_application.exception.ResourceNotFoundException;
import com.ecom_application.exception.UserNotFoundException;
import com.ecom_application.repository.CartItemRepository;
import com.ecom_application.repository.ProductRepository;
import com.ecom_application.repository.UserRepository;
import com.ecom_application.service.CartItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

	private final ProductRepository productRepo;

	private final UserRepository userRepo;

	private final CartItemRepository cartRepo;

	private final ProductServiceImpl productService;

	@Override
	public void addToCart(Integer userId, CartItemRequest cartItemRequest)
			throws ResourceNotFoundException, UserNotFoundException {

		Product product = productRepo.findById(cartItemRequest.getProductId()).orElseThrow(
				() -> new ResourceNotFoundException("Product", "ProductId", cartItemRequest.getProductId()));

		if (product.getStockQuantity() < cartItemRequest.getQuantity()) {
			throw new ResourceNotFoundException(
					String.format("Requested quantity %d is not available in stock :: Stock Avaliable %d",
							cartItemRequest.getQuantity(), product.getStockQuantity()));
		}

		if (!product.getActive()) {
			throw new ResourceNotFoundException(String.format("Requested product is outoff stock"));
		}

		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User", "UserId", userId));

		CartItem existingCartItem = cartRepo.findByUserAndProduct(user, product);

		if (existingCartItem != null) {
			// Updated Cart Quantity
			existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());

			// Reducing ProductQuantity
			product.setStockQuantity(product.getStockQuantity() - cartItemRequest.getQuantity());
			ProductResponse updatedProduct = productService.updateProduct(product, product.getId());

			existingCartItem.setProduct(productService.mapProductResponseToProduct(updatedProduct));
			existingCartItem.setUser(user);
			existingCartItem.setPrice((product.getPrice()) * (existingCartItem.getQuantity()));

			cartRepo.save(existingCartItem);

		} else {
			CartItem cartItem = mapCartRequestToCartEntity(cartItemRequest);
			cartItem.setUser(user);
			cartItem.setProduct(product);

			cartItem.setPrice((product.getPrice()) * (cartItem.getQuantity()));

			product.setStockQuantity(product.getStockQuantity() - cartItemRequest.getQuantity());
			// Updating Product
			productService.updateProduct(product, product.getId());

			cartRepo.save(cartItem);

		}

		return;

	}

	private CartItem mapCartRequestToCartEntity(CartItemRequest cartItemRequest) {
		CartItem cartItem = new CartItem();
		cartItem.setQuantity(cartItemRequest.getQuantity());

		return cartItem;

	}

	@Override
	public List<CartItem> getAllCarts() {
		return cartRepo.findAll();
	}

	@Override
	public void deleteCartItem(Integer cartItemId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		CartItem cartItem = cartRepo.findById(cartItemId)
				.orElseThrow(() -> new ResourceNotFoundException("CartItem", "CartId", cartItemId));

		Product product = productRepo.findById(cartItem.getProduct().getId()).get();

		product.setStockQuantity(product.getStockQuantity() + cartItem.getQuantity());

		productService.updateProduct(product, product.getId());

		cartRepo.delete(cartItem);
	}

	public void deleteAllCartItem(List<CartItem> listCartItem) throws ResourceNotFoundException {

		cartRepo.deleteAll(listCartItem);
	}

	@Override
	public void updateCartItem(Integer cartItemId, CartItemRequest cartItemRequest) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		CartItem cartItem = cartRepo.findById(cartItemId)
				.orElseThrow(() -> new ResourceNotFoundException("CartItem", "CartId", cartItemId));

		Product product = productRepo.findById(cartItem.getProduct().getId()).get();

		if (cartItemRequest.getQuantity() > cartItem.getQuantity()) {
			product.setStockQuantity(
					product.getStockQuantity() - (cartItemRequest.getQuantity() - cartItem.getQuantity()));

		} else {
			product.setStockQuantity(
					product.getStockQuantity() + (cartItem.getQuantity() - cartItemRequest.getQuantity()));
		}

		cartItem.setQuantity(cartItemRequest.getQuantity());

		productService.updateProduct(product, product.getId());

		cartRepo.save(cartItem);
	}

	@Override
	public List<CartItem> findCartByUserId(Integer userId) throws UserNotFoundException, ResourceNotFoundException {

		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User", "UserId", userId));

		List<CartItem> listOfCartItems = cartRepo.findByUser(user);

		if (listOfCartItems.isEmpty()) {
			throw new ResourceNotFoundException("CART Item is empty for this user with userID : " + userId);
		}

		return listOfCartItems;

	}

}
