package com.ecom_application.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom_application.dto.OrderStatus;
import com.ecom_application.entity.CartItem;
import com.ecom_application.entity.Order;
import com.ecom_application.entity.OrderItem;
import com.ecom_application.entity.User;
import com.ecom_application.exception.ResourceNotFoundException;
import com.ecom_application.exception.UserNotFoundException;
import com.ecom_application.repository.CartItemRepository;
import com.ecom_application.repository.OrderRepository;
import com.ecom_application.repository.UserRepository;
import com.ecom_application.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final UserRepository userRepo;
	private final CartItemServiceImpl cartItemServiceImpl;
	private final OrderRepository orderRepo;
	private final CartItemRepository cartRepo;

	@Override
	@Transactional
	public void createOrder(Integer userId) throws UserNotFoundException, ResourceNotFoundException {

		// Fetch User
		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User", "userId", userId));

		// Fetch Cart Items
		List<CartItem> cartItems = cartItemServiceImpl.findCartByUserId(userId);

		// Check Cart Empty
		if (cartItems.isEmpty()) {
			throw new ResourceNotFoundException("Cart", "userId", userId);
		}

		// Create Order
		Order order = new Order();

		order.setUser(user);

		// Set Total Amount
		order.setTotalAmount(calculateTotalAmount(cartItems));

		// Set Order Status
		order.setStatus(OrderStatus.CONFIRMED);

		// Create Order Items
		order.setItems(createOrderItems(cartItems, order));

		// Save Order
		orderRepo.save(order);

		// Clear Cart
		cartItemServiceImpl.deleteAllCartItem(cartItems);
	}

	private Double calculateTotalAmount(List<CartItem> cartItems) {

		return cartItems.stream().mapToDouble(CartItem::getPrice).sum();
	}

	private List<OrderItem> createOrderItems(List<CartItem> cartItems, Order order) {

		return cartItems.stream().map(cartItem -> {

			OrderItem orderItem = new OrderItem();

			orderItem.setProduct(cartItem.getProduct());

			orderItem.setQuantity(cartItem.getQuantity());

			orderItem.setPrice(cartItem.getPrice());

			orderItem.setOrder(order);

			return orderItem;

		}).toList();
	}

	@Override
	public List<Order> getOrderUsingUserId(Integer userId) throws UserNotFoundException {
		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User", "userId", userId));
		List<Order> listOfOrders = orderRepo.findByUser(user);

		return listOfOrders;
	}
}