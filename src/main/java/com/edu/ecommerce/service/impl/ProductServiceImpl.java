package com.edu.ecommerce.service.impl;

import com.edu.ecommerce.exceptions.CrmException;
import com.edu.ecommerce.exceptions.ResourceNotFoundException;
import com.edu.ecommerce.model.Category;
import com.edu.ecommerce.model.Product;
import com.edu.ecommerce.repository.CategoryRepository;
import com.edu.ecommerce.repository.ProductRepository;
import com.edu.ecommerce.service.interfaces.DataService;
import com.edu.ecommerce.service.interfaces.ProductService;
import com.edu.ecommerce.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final DataService dataService;
    private final UserService userService;


    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Product with id: %s not found!", id)));
    }

    @Override
    public List<Product> getAllProductByCategoryId(Long id) {
        var byId = categoryRepository.findById(id);
        if (byId.isPresent()) {
            var category =  byId.get();
            return productRepository.findAllByCategory(category);
        } else {
            throw new ResourceNotFoundException(String.format("Category with id: %s not found!", id));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Product findByName(String productName) {
        return productRepository.findProductByName(productName);
    }

    @Transactional
    @Override
    public Product create(Product product) {
        checkProductExist(product);
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public Product update(Long id, Product product) {
        return productRepository.save(updateProductFields(findById(id), product));
    }


    private Product updateProductFields(Product findProduct, Product request) {
        return Product.builder()
                .id(findProduct.getId())
                .name(Optional.ofNullable(request.getName()).orElse(findProduct.getName()))
                .description(Optional.ofNullable(request.getDescription()).orElse(findProduct.getDescription()))
                .category(Optional.ofNullable(request.getCategory()).orElse(findProduct.getCategory()))
                .price(Optional.of(request.getPrice()).orElse(findProduct.getPrice()))
                .imageURL(Optional.ofNullable(request.getImageURL()).orElse(findProduct.getImageURL()))
                .build();
    }

    @Transactional
    @Override
    public Product delete(Long id) {
        var findProduct = findById(id);
        productRepository.deleteById(id);
        return findProduct;
    }

    private void checkProductExist (Product product) {
        if (IsProductAlreadyExist(product)) {
            throw new CrmException(String.format("Product with name = %s is already exist.", product.getName()));
        }
    }

    private boolean IsProductAlreadyExist(Product product) {
        var category = categoryRepository.findById(product.getCategory().getId());
        var count = productRepository.countByNameAndCategory(product.getName(), category);
        return Objects.nonNull(count) && count > 0;
    }

}
