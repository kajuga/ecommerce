package com.edu.ecommerce.service.interfaces;

import com.edu.ecommerce.model.Product;

import java.util.List;

public interface ProductService {

    /**
     * Return all products
     * @return list of products
     */
    List<Product> findAll();

    /**
     * Return product by id
     * @param id to find the Product
     * @return product for transmitted parameter
     */
    Product findById(Long id);

    /**
     * Return Product for transmitted name
     * @param productName for findProduct
     * @return product for transmitted name (parameter)
     */
    Product findByName(String productName);

    /**
     * @param id - category id
     * @return list all of products if transmitted category
     */
    /**
     * Return list of users by Role.
     * @return list of users
     */
    List<Product> getAllProductByCategoryId(Long id);

    /**
     * Save created Product
     * @param  - Product for create/save
     * @return created/saved Product
     */
    Product create (Product product);

    /**
     * Updates transmitted Product.
     *
     * @param id   Product id for update
     * @param product for update
     * @return updated permission
     */
    Product update(Long id, Product product);

    /**
     * Delete Product by id.
     * @return
     */
    Product delete(Long id);

}
