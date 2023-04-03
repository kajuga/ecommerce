package com.edu.userservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", length = 55)
    private String name;

    @Column(name = "description")
    private String description;

}
