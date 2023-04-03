package com.edu.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    ADMINISTRATOR("ADMINISTRATOR"),
    MANAGER("MANAGER"),
    SPECIALIST("SPECIALIST"),
    EXTERNAL("EXTERNAL_");

    private String name;
}