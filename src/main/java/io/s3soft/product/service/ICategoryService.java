package io.s3soft.product.service;
import java.util.List;
import io.s3soft.product.model.Category;

/**
 * @author shaiksha
 *
 */
public interface ICategoryService {
	public Category saveCategory(Category category);
	public List<Category> getAllCategories();
	public Category getCategoryById(String categoryId);
	public void deleteCategory(String categoryId);
	public Category upadateCategory(Category category);
}
