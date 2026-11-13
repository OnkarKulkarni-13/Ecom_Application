package com.ecom_application.controller;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ecom_application.dto.ResponseDTO;
import com.ecom_application.entity.Order;
import com.ecom_application.exception.ResourceNotFoundException;
import com.ecom_application.exception.UserNotFoundException;
import com.ecom_application.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/order")
	public ResponseEntity<ResponseDTO> createOrder(@RequestHeader("X-USER-ID") Integer userId)
			throws UserNotFoundException, ResourceNotFoundException {
		orderService.createOrder(userId);

		return new ResponseEntity<ResponseDTO>(
				new ResponseDTO("Order Created Successfully for User with UserID : " + userId, new Date(), true),
				HttpStatus.CREATED);
	}

	@GetMapping("/order")
	public ResponseEntity<List<Order>> getOrderUsingUserId(@RequestHeader("X-USER-ID") Integer userId)
			throws UserNotFoundException {
		List<Order> orderUsingUserId = orderService.getOrderUsingUserId(userId);

		return new ResponseEntity<List<Order>>(orderUsingUserId, HttpStatus.OK);

	}

}
