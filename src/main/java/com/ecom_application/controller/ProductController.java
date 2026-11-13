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
import org.springframework.web.bind.annotation.RestController;

import com.ecom_application.dto.ProductResponse;
import com.ecom_application.dto.ResponseDTO;
import com.ecom_application.entity.Product;
import com.ecom_application.exception.ResourceNotFoundException;
import com.ecom_application.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping("/createProduct")
	public ResponseEntity<ProductResponse> createProduct(@RequestBody Product product) {
		ProductResponse productCreated = productService.createProduct(product);

		return new ResponseEntity<>(productCreated, HttpStatus.CREATED);

	}

	@GetMapping("/product")
	public ResponseEntity<List<ProductResponse>> getAllProducts() throws ResourceNotFoundException {
		List<ProductResponse> listProducts = productService.getAllProducts();

		return new ResponseEntity<List<ProductResponse>>(listProducts, HttpStatus.OK);
	}

	@PutMapping("/updateProduct/{id}")
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Integer productId,
			@RequestBody Product product) throws ResourceNotFoundException {
		ProductResponse updatedProduct = productService.updateProduct(product, productId);

		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Integer productId)
			throws ResourceNotFoundException {
		ProductResponse product = productService.getProductById(productId);

		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<ResponseDTO> deleteProductById(@PathVariable("id") Integer productId)
			throws ResourceNotFoundException {
		productService.deleteProductById(productId);

		return new ResponseEntity<>(new ResponseDTO("Product deleted successfully", new Date(), true), HttpStatus.OK);
	}

	@GetMapping("/searchProduct/{keyword}")
	public ResponseEntity<List<ProductResponse>> getProductList(@PathVariable("keyword") String keyword)
			throws ResourceNotFoundException {
		List<ProductResponse> allProductByKeyword = productService.getAllProductByKeyword(keyword);

		return new ResponseEntity<List<ProductResponse>>(allProductByKeyword, HttpStatus.OK);

	}

}
