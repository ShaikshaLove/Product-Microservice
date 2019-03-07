package io.s3soft.product.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.s3soft.product.model.Product;
import io.s3soft.product.repository.ProductRepository;
import io.s3soft.product.service.IProductService;
@Service
@Transactional
public class ProductServiceImpl implements IProductService {
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product saveProduct(Product product) {
		product.setCreatedDate(new Date());
		return productRepository.save(product);
	}

	@SuppressWarnings("resource")
	@Override
	public List<Product> getAllProducts() {
		List<Product> products=productRepository.findAll();
		return products;
	}

	@Override
	public Product getProductById(String productId) {
	;
		return productRepository.findById(productId).get();
	}

	@Override
	public void deleteProduct(String productId) {
		productRepository.deleteById(productId);
	}

	@Override
	public Product upadateProduct(Product product) {
		product.setCreatedDate(getProductById(product.getProductId()).getCreatedDate());
		product.setLastModifiedDate(new Date());
		return productRepository.save(product);
	}


}
