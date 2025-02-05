package org.example.product.entity;

import lombok.Data;

@Data
public class Product {
    /** 自增ID */
    private Integer id;
    private String name;
    private String description;
    private String picture;
    private Long price;
}
