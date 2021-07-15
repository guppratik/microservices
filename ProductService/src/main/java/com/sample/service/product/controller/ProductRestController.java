
package com.sample.service.product.controller;

import com.sample.service.product.repository.ProductRepository;
import com.sample.service.product.model.Product;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
//@RequestMapping("/products")
public class ProductRestController {

	@Autowired
	private ProductRepository productRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);

    //------------------- Retreive a Product --------------------------------------------------------
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource<Product>> getProduct(@PathVariable("id") String id) {

    	LOGGER.info("Start");
    	LOGGER.debug("Fetching Product with id: {}", id);

        Product product = productRepository.findOne(id);
        if (product == null) {
    		LOGGER.debug("Product with id: {} not found", id);
            return new ResponseEntity<Resource<Product>>(HttpStatus.NOT_FOUND);
        }
        Resource<Product> productRes =new Resource<Product>(product, linkTo(methodOn(ProductRestController.class).getProduct(product.getId())).withSelfRel());
    	LOGGER.info("Ending");
        return new ResponseEntity<Resource<Product>>(productRes, HttpStatus.OK);
    }

    //------------------- Create a Product --------------------------------------------------------
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<Resource<Product>> postProduct(@RequestBody Product product,    UriComponentsBuilder ucBuilder) {

    	LOGGER.info("Start");
    	LOGGER.debug("Creating Product with code: {}", product.getCode());

        List<Product> products = productRepository.findByCode(product.getCode());
        if (products.size() > 0) {
    		LOGGER.debug("A Product with code {} already exist", product.getCode());
            return new ResponseEntity<Resource<Product>>(HttpStatus.CONFLICT);
        }

        Product newProduct = productRepository.save(product);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri());
        Resource<Product> productRes =new Resource<Product>(newProduct, linkTo(methodOn(ProductRestController.class).getProduct(newProduct.getId())).withSelfRel());
    	LOGGER.info("Ending");
        return new ResponseEntity<Resource<Product>>(productRes, headers, HttpStatus.OK);
    }

    //------------------- Update a Product --------------------------------------------------------
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Resource<Product>> updateProduct(@PathVariable("id") String id, @RequestBody Product product) {

    	LOGGER.info("Start");
    	LOGGER.debug("Updating Product with id: {}", id);

        Product currentProduct = productRepository.findOne(id);

        if (currentProduct == null) {
    		LOGGER.debug("Product with id: {} not found", id);
            return new ResponseEntity<Resource<Product>>(HttpStatus.NOT_FOUND);
        }

        currentProduct.setName(product.getName());
        currentProduct.setCode(product.getCode());
        currentProduct.setTitle(product.getTitle());
        currentProduct.setDescription(product.getDescription());
        currentProduct.setImgUrl(product.getImgUrl());
        currentProduct.setPrice(product.getPrice());
        currentProduct.setProductCategoryName(product.getProductCategoryName());

        Product newProduct = productRepository.save(currentProduct);

        Resource<Product> productRes =new Resource<Product>(newProduct, linkTo(methodOn(ProductRestController.class).getProduct(newProduct.getId())).withSelfRel());
    	LOGGER.info("Ending");
        return new ResponseEntity<Resource<Product>>(productRes, HttpStatus.OK);
    }

    //------------------- Retreive all Products --------------------------------------------------------
    @RequestMapping(value = "/products", method = RequestMethod.GET ,produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Resources<Resource<Product>>> getAllProducts() {

    	LOGGER.info("Start");
        List<Product> products = productRepository.findAll();
        Link links[]={linkTo(methodOn(ProductRestController.class).getAllProducts()).withSelfRel(),linkTo(methodOn(ProductRestController.class).getAllProducts()).withRel("getAllProducts")};
        if(products.isEmpty()){
    		LOGGER.debug("No products retreived from repository");
            return new ResponseEntity<Resources<Resource<Product>>>(HttpStatus.NOT_FOUND);
        }
        List<Resource<Product>> list=new ArrayList<Resource<Product>> ();
        for(Product product:products){
        	list.add(new Resource<Product>(product, linkTo(methodOn(ProductRestController.class).getProduct(product.getId())).withSelfRel()));
        }
        Resources<Resource<Product>> productRes=new Resources<Resource<Product>>(list, links) ;
    	LOGGER.info("Ending");
        return new ResponseEntity<Resources<Resource<Product>>>(productRes, HttpStatus.OK);
    }

    //------------------- Delete a Product --------------------------------------------------------
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") String id) {

    	LOGGER.info("Start");
    	LOGGER.debug("Fetching & Deleting Product with id: {}", id);
        Product product = productRepository.findOne(id);
        if (product == null) {
    		LOGGER.debug("Product with id: {} not found, hence not deleted", id);
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        productRepository.delete(id);
    	LOGGER.info("Ending");
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }

    //------------------- Delete All Products --------------------------------------------------------
    @RequestMapping(value = "/products", method = RequestMethod.DELETE)
    public ResponseEntity<Product> deleteAllProducts() {

    	LOGGER.info("Start");
        long count = productRepository.count();
        LOGGER.debug("Deleting {} products", count);
        productRepository.deleteAll();
    	LOGGER.info("Ending");
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }
}
