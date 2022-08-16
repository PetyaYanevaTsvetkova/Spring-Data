package com.example.demo.service.impl;

import com.example.demo.constants.GlobalConstants;
import com.example.demo.model.dto.ProductSeedDTO;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ValidationUtil validationUtil, Gson gson, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedProductData() throws IOException {
        if (productRepository.count() > 0) {
            return;
        }

        String fileContent = Files.readString(Path.of(GlobalConstants.INPUT_FILES_PATH + GlobalConstants.PRODUCTS));

        ProductSeedDTO[] productSeedDTOS = gson.fromJson(fileContent, ProductSeedDTO[].class);

        Arrays.stream(productSeedDTOS)
                .filter(validationUtil::isValid)
                .map(productSeedDTO -> modelMapper.map(productSeedDTO, Product.class))
                .forEach(productRepository::save);
    }

    @Override
    public Set<Product> findRandomProducts() {
        Set<Product> products = new HashSet<>();
        int productCount = ThreadLocalRandom.current().nextInt(1, 6);
        long totalProducts = productRepository.count();

        for (int i = 0; i < productCount; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, totalProducts + 1);

            products.add(productRepository.findById(randomId).orElse(null));
        }
        return products;
    }


}
