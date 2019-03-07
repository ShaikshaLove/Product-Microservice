/**
 * 
 */
package io.s3soft.product.service.impl;


import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.s3soft.product.model.Category;
import io.s3soft.product.repository.CategoryRepository;
import io.s3soft.product.service.ICategoryService;

/**
 * @author Shaiksha
 *
 */
@Service
public class CategoryServiceImpl implements ICategoryService {
	

	
	@Autowired
	private CategoryRepository categoryRepository;

	/* (non-Javadoc)
	 * @see io.s3soft.product.service.ICategoryService#saveCategory(io.s3soft.product.model.Category)
	 */
	@Override
	public Category saveCategory(Category category) {
		category.setCreatedDate(new Date());
		return categoryRepository.save(category);
	}

	/* (non-Javadoc)
	 * @see io.s3soft.product.service.ICategoryService#getAllCategories()
	 */
	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	/* (non-Javadoc)
	 * @see io.s3soft.product.service.ICategoryService#getCategoryById(java.lang.String)
	 */
	@Override
	public Category getCategoryById(String categoryId) {
		return categoryRepository.findById(categoryId).get();
	}

	/* (non-Javadoc)
	 * @see io.s3soft.product.service.ICategoryService#deleteCategory(java.lang.String)
	 */
	@Override
	public void deleteCategory(String categoryId) {
		categoryRepository.deleteById(categoryId);
	}

	/* (non-Javadoc)
	 * @see io.s3soft.product.service.ICategoryService#upadateCategory(io.s3soft.product.model.Category)
	 */
	@Override
	public Category upadateCategory(Category category) {
		category.setCreatedDate(getCategoryById(category.getId()).getCreatedDate());
		category.setLastModifiedDate(new Date());
		return categoryRepository.save(category);
	}



}
