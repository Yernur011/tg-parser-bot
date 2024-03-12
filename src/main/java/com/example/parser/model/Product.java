package com.example.parser.model;

import lombok.*;

import java.util.UUID;

@Data
public class Product {
    private String id = UUID.randomUUID().toString();
    private String productName;
    private String productLink;
    private String status;
    private String address;
}
