package io.s3soft.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.s3soft.product.model.Category;

/**
 * @author shaiksha
 *
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category,String>{

}
