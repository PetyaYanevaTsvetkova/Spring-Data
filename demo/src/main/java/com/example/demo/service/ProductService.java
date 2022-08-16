package com.example.demo.service;

import com.example.demo.model.entity.Product;

import java.io.IOException;
import java.util.Set;

public interface ProductService {
    void seedProductData() throws IOException;

    Set<Product> findRandomProducts();
}
