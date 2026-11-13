package com.ecom_application.service;

import java.util.List;

import com.ecom_application.entity.Order;
import com.ecom_application.exception.ResourceNotFoundException;
import com.ecom_application.exception.UserNotFoundException;

public interface OrderService {

	public void createOrder(Integer userId) throws UserNotFoundException, ResourceNotFoundException;

	public List<Order> getOrderUsingUserId(Integer userId) throws UserNotFoundException;

}
