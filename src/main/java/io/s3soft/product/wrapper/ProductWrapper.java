package io.s3soft.product.wrapper;

import java.util.List;

import io.s3soft.product.model.Product;

public class ProductWrapper {

	private List<Product> products;

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public ProductWrapper(List<Product> products) {
		super();
		this.products = products;
	}

	public ProductWrapper() {
		super();
	}
}
