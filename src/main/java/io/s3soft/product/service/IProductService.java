package io.s3soft.product.service;

import java.util.List;

import io.s3soft.product.model.Product;

public interface IProductService {
	public Product saveProduct(Product product);
	public List<Product> getAllProducts();
	public Product getProductById(String productId);
	public void deleteProduct(String productId);
	public Product upadateProduct(Product product);
}
