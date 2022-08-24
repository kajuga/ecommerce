package com.edu.ecommerce.repository;

import com.edu.ecommerce.model.Category;
import com.edu.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductByName(String name);

    List<Product> findAllByCategory(Category category);

    Long countByNameAndCategory(String productName, Optional optional);

}
