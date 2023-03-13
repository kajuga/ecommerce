package com.edu.ecommerce.service.interfaces;

import com.edu.ecommerce.model.Category;

import java.util.List;

public interface CategoryService {

    /**
     * Returns all categories.
     *
     * @return list of categories
     */
    List<Category> findAll();

    /**
     * Return category for transmitted id.
     *
     * @param id for find category
     * @return category for transmitted parameter
     */
    Category findById(Long id);


    /**
     * Return category for transmitted name.
     *
     * @param  categoryName for find category
     * @return category by transmitted parameter - name
     */
    Category findByName(String categoryName);

    /**
     * Saves transmitted category.
     *
     * @param category for create
     * @return createdcategory
     */
    Category create(Category category);

    /**
     * Updates transmitted category.
     *
     * @param id   category id for update
     * @param category for update
     * @return updated permission
     */
    Category update(Long id, Category category);

    /**
     * Deletes category by id.
     */
    void delete(Long id);
}
