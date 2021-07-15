
package com.sample.service.product.repository;

import java.util.List;

import com.sample.service.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "productdata", path = "productdata")
public interface ProductRepository extends MongoRepository<Product, String>  {

	public List<Product> findByProductCategoryName(@Param("productCategory") String  productCatagoryName);
	public List<Product> findByCode(@Param("code") String  code);


}
