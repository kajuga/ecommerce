package com.edu.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {

    @JsonProperty("token")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("expires")
    private long expires;

    @JsonProperty("role")
    private String role;

    @JsonProperty("id")
    private Long userId;
}