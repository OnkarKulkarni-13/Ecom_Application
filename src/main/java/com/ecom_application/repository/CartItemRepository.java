package com.ecom_application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecom_application.entity.CartItem;
import com.ecom_application.entity.Product;
import com.ecom_application.entity.User;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	@Query("SELECT c FROM CartItem c WHERE c.user = :user AND c.product = :product")
	CartItem findByUserAndProduct(@Param("user") User user, @Param("product") Product product);

	List<CartItem> findByUser(User user);

}
