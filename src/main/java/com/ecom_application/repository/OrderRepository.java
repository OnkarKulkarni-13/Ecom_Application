package com.ecom_application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom_application.entity.Order;
import com.ecom_application.entity.User;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	List<Order> findByUser(User user);

}
