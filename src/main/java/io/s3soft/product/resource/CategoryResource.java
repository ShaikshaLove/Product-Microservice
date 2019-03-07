package io.s3soft.product.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;

import io.s3soft.product.model.Category;
import io.s3soft.product.model.Product;
import io.s3soft.product.service.ICategoryService;
import io.s3soft.product.wrapper.CategoryWrapper;
import io.s3soft.product.wrapper.ProductWrapper;

/**
 * @author shaiksha
 *
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryResource implements ServletContextAware {

	private String imagePath;
	@Autowired
	private ICategoryService categoryService;

	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
		
		return new ResponseEntity<Category>(categoryService.saveCategory(category),HttpStatus.CREATED);
	}

	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public CategoryWrapper getAllCategories() {
		CategoryWrapper categoryWrapper=new CategoryWrapper();
		List<Category> categories=categoryService.getAllCategories();
		categories.forEach((category)->{
			List<Product> products=category.getProducts();
			products.forEach((product)->{
				File imageDirectory=new File(imagePath,"images");
				if(!imageDirectory.exists()) {
					imageDirectory.mkdir();
				}
				File image=new File(imageDirectory,product.getImageName());
				if(!image.exists()) {
					FileOutputStream fos=null;
					try {
						image.createNewFile();
						fos=new FileOutputStream(image);
						fos.write(product.getImage());
						fos.flush();
					} catch (IOException e) {
						try {
							fos.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						e.printStackTrace();
					}
				}
			    product.setImage(null);
			    product.setCategories(null);
			});
			
		});
		categoryWrapper.setCategories(categories);
		return categoryWrapper;
	}
	@GetMapping(value="/{categoryId}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Category> getCategoryById(@PathVariable("categoryId")String categoryId){
		Category category=categoryService.getCategoryById(categoryId);
		List<Product> products=category.getProducts();
		products.forEach((product)->{
			File imageDirectory=new File(imagePath,"images");
			if(!imageDirectory.exists()) {
				imageDirectory.mkdir();
			}
			File image=new File(imageDirectory,product.getImageName());
			if(!image.exists()) {
				FileOutputStream fos=null;
				try {
					image.createNewFile();
					fos=new FileOutputStream(image);
					fos.write(product.getImage());
					fos.flush();
				} catch (IOException e) {
					try {
						fos.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
		   product.setImage(null);
		   product.setCategories(null);
		});
		return new ResponseEntity<Category>(category,HttpStatus.OK);
	}
	@DeleteMapping(value="/{categoryId}")
	public ResponseEntity<Optional<String>> deleteCategoryById(@PathVariable("categoryId")String categoryId){
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<Optional<String>>(Optional.of("Record has been removed "+categoryId),HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<Optional<String>> updateCategory(@RequestBody Category category){
		categoryService.upadateCategory(category);
		return new ResponseEntity<Optional<String>>(Optional.of("The Data updated"),HttpStatus.OK);
	}
	
	@GetMapping(value="/{categoryId}/products",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductWrapper> getAllProductsCategoryById(@PathVariable("categoryId")String categoryId){
		ProductWrapper productWrapper=new ProductWrapper();
		List<Product> products=categoryService.getCategoryById(categoryId).getProducts();
		products.forEach((product)->{
			File imageDirectory=new File(imagePath,"images");
			if(!imageDirectory.exists()) {
				imageDirectory.mkdir();
			}
			File image=new File(imageDirectory,product.getImageName());
			if(!image.exists()) {
				FileOutputStream fos=null;
				try {
					image.createNewFile();
					fos=new FileOutputStream(image);
					fos.write(product.getImage());
					fos.flush();
				} catch (IOException e) {
					try {
						fos.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
		   product.setImage(null);
		   product.setCategories(null);
		});
		productWrapper.setProducts(products);
		return new ResponseEntity<ProductWrapper>(productWrapper,HttpStatus.OK);
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		imagePath=servletContext.getRealPath("/");
	}

}
