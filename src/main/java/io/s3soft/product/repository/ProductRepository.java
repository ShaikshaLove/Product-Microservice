package io.s3soft.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.s3soft.product.model.Product;

/**
 * @author shaiksha
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

}
