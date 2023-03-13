package com.edu.ecommerce.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", length = 55)
    private String categoryName;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

}
