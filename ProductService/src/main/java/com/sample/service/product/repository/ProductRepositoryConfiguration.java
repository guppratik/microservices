
package com.sample.service.product.repository;

import com.sample.service.product.model.Product;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;


@Configuration
public class ProductRepositoryConfiguration extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Product.class);
    }
}
