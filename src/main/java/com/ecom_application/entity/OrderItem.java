package com.ecom_application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderItemid;
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	private Integer quantity;
	private Double price;
	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	@JsonIgnore(value = true)
	private Order order;

}
