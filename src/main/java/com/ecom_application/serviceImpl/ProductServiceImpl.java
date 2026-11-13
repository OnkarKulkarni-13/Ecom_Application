package com.ecom_application.serviceImpl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecom_application.dto.ProductResponse;
import com.ecom_application.entity.Product;
import com.ecom_application.exception.ResourceNotFoundException;
import com.ecom_application.repository.ProductRepository;
import com.ecom_application.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepo;

	@Override
	public ProductResponse createProduct(Product product) {
		Product productCreated = productRepo.save(product);

		return mapProductToProductResponse(productCreated);

	}

	@Override
	public List<ProductResponse> getAllProducts() throws ResourceNotFoundException {
		List<Product> listOfProduct = productRepo.findAll();
		if (listOfProduct.isEmpty()) {
			throw new ResourceNotFoundException("Product Records are empty ");
		}

		return listOfProduct.stream().map(this::mapProductToProductResponse).collect(Collectors.toList());

	}

	@Override
	public ProductResponse updateProduct(Product product, Integer productId) throws ResourceNotFoundException {
		Product currentProduct = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));

		Product updateProduct = updateCurrentProductWithProductRequest(currentProduct, product);

		productRepo.save(updateProduct);

		return mapProductToProductResponse(updateProduct);
	}

	@Override
	public ProductResponse getProductById(Integer productId) throws ResourceNotFoundException {
		Product Product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));

		return mapProductToProductResponse(Product);
	}

	@Override
	public void deleteProductById(Integer productId) throws ResourceNotFoundException {
		Product Product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));

		productRepo.delete(Product);

		return;
	}

	public ProductResponse mapProductToProductResponse(Product product) {
		ProductResponse productResponse = new ProductResponse();
		productResponse.setActive(product.getActive());
		productResponse.setCatagory(product.getCatagory());
		productResponse.setCreatedAt(product.getCreatedAt());
		productResponse.setDescription(product.getDescription());
		productResponse.setId(product.getId());
		productResponse.setImageUrl(product.getImageUrl());
		productResponse.setName(product.getName());
		productResponse.setPrice(product.getPrice());
		productResponse.setStockQuantity(product.getStockQuantity());
		productResponse.setUpdatedAt(product.getUpdatedAt());

		return productResponse;
	}

	@Override
	public List<ProductResponse> getAllProductByKeyword(String keyword) throws ResourceNotFoundException {
		List<Product> listOfProducts = productRepo.findByKeyword(keyword);
		if (listOfProducts.isEmpty()) {
			throw new ResourceNotFoundException("Product Records are empty ");
		}

		return listOfProducts.stream().map(this::mapProductToProductResponse).collect(Collectors.toList());
	}

	private Product updateCurrentProductWithProductRequest(Product currentProduct, Product productRequest) {
		currentProduct.setActive(productRequest.getActive());
		currentProduct.setCatagory(productRequest.getCatagory());
		currentProduct.setDescription(productRequest.getDescription());
		currentProduct.setImageUrl(productRequest.getImageUrl());
		currentProduct.setName(productRequest.getName());
		currentProduct.setPrice(productRequest.getPrice());
		currentProduct.setStockQuantity(productRequest.getStockQuantity());

		return currentProduct;
	}

	public Product mapProductResponseToProduct(ProductResponse productResponse) {
		Product product = new Product();
		product.setActive(productResponse.getActive());
		product.setCatagory(productResponse.getCatagory());
		product.setCreatedAt(productResponse.getCreatedAt());
		product.setDescription(productResponse.getDescription());
		product.setId(productResponse.getId());
		product.setImageUrl(productResponse.getImageUrl());
		product.setName(productResponse.getName());
		product.setPrice(productResponse.getPrice());
		product.setStockQuantity(productResponse.getStockQuantity());
		product.setUpdatedAt(productResponse.getUpdatedAt());

		return product;
	}

}
