package com.ecom_application.service;

import java.util.List;

import com.ecom_application.dto.ProductResponse;
import com.ecom_application.entity.Product;
import com.ecom_application.exception.ResourceNotFoundException;

public interface ProductService {
	public ProductResponse createProduct(Product product);

	public List<ProductResponse> getAllProducts() throws ResourceNotFoundException;

	public ProductResponse updateProduct(Product product, Integer productId) throws ResourceNotFoundException;

	public ProductResponse getProductById(Integer productId) throws ResourceNotFoundException;

	void deleteProductById(Integer productId) throws ResourceNotFoundException;

	public List<ProductResponse> getAllProductByKeyword(String keyword) throws ResourceNotFoundException;

}
