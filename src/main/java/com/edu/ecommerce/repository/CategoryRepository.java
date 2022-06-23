package com.edu.ecommerce.repository;

import com.edu.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

//    Category findByCategoryName(String categoryName);

}
