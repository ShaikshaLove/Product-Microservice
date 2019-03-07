package io.s3soft.product.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import io.s3soft.product.model.Product;
import io.s3soft.product.service.IProductService;
import io.s3soft.product.wrapper.ProductWrapper;

/**
 * @author shaiksha
 *
 */
@RestController
@RequestMapping("/api/products")
public class ProductResource implements InitializingBean,ServletContextAware {

	@Autowired 
	private Environment env;
	private String imagePath;
	private String imageLocation;

	@Autowired
	private IProductService productService;

	@SuppressWarnings("resource")
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<ProductWrapper > getAllProducts(){
		ProductWrapper productWrapper=new ProductWrapper();
		List<Product> products=productService.getAllProducts();
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
					fos.close();
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
		return ResponseEntity.ok(productWrapper);
	}

	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
		Product p=productService.saveProduct(product);
		p.setImage(null);
		return new ResponseEntity<Product>(p,HttpStatus.CREATED);
	}



	@GetMapping(value="/{productId}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Optional<Product>> getProductById(@PathVariable("productId")String productId){
		Product product=productService.getProductById(productId);
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
				fos.close();
			} catch (IOException e) {
				try {
					fos.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
		}
		product.setImage(null);	
		product.setCategories(null);
		return new ResponseEntity<Optional<Product>>(Optional.of(product),HttpStatus.OK);
	}

	@DeleteMapping(value="/{productId}")
	public ResponseEntity<Optional<String>> deleteProductById(@PathVariable("productId")String productId){
		productService.deleteProduct(productId);
		return new ResponseEntity<Optional<String>>(Optional.of("Record has been removed "+productId),HttpStatus.OK);
	}

	@PutMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> updateProduct(@RequestBody Product product){
		Product p=productService.upadateProduct(product);
		p.setImage(null);
		p.setCategories(null);
		return new ResponseEntity<Product>(p,HttpStatus.OK);
	}

	@PostMapping(value="/upload",consumes=MediaType.ALL_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> uploadImage(@RequestParam("file")MultipartFile imageFile,@RequestParam("productId")String productId) {
		Product product=productService.getProductById(productId);
		try {
	           StringTokenizer st=new StringTokenizer(imageFile.getOriginalFilename(),".");
	           String extention="jpg";
	           while(st.hasMoreTokens()) {
	        	   String token=st.nextToken();
	        	   if(token.equals("jpg")) {
	        		   extention=".jpg";
	        	   }
	        	   if(token.equals("png")) {
	        		   extention=".png";
	        	   }
	           }
	           product.setImage(imageFile.getBytes());
			product.setImageName(productId+""+extention);
			product.setImageLocation(imageLocation+""+productId+""+extention);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Product p=productService.upadateProduct(product);
		p.setImage(null);
		p.setCategories(null);
		return new ResponseEntity<Product>(p,HttpStatus.OK);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		imageLocation=env.getProperty("image.location");		
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		imagePath=servletContext.getRealPath("/");		
	}
}
