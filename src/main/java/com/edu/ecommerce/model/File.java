package com.edu.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private Long size;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}

