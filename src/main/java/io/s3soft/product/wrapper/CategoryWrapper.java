package io.s3soft.product.wrapper;

import java.util.List;

import io.s3soft.product.model.Category;

/**
 * @author shaiksha
 *
 */
public class CategoryWrapper {
	private List<Category> categories;

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "CategoryWrapper [categories=" + categories + "]";
	}

	public CategoryWrapper(List<Category> categories) {
		super();
		this.categories = categories;
	}

	public CategoryWrapper() {
		super();
	}
}
