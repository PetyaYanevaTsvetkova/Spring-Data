package com.example.demo.service.impl;

import com.example.demo.constants.GlobalConstants;
import com.example.demo.model.dto.OrderSeedDTO;

import com.example.demo.model.entity.Orders;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.CustomerService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final CustomerService customerService;
    private final ValidationUtil validationUtil;
    private final ProductService productService;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, Gson gson, CustomerService customerService, ValidationUtil validationUtil, ProductService productService) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.customerService = customerService;
        this.validationUtil = validationUtil;
        this.productService = productService;
    }

    @Override
    public void seedOrderDate() throws IOException {
        if (orderRepository.count() > 0) {
            return;
        }

        String fileContent = Files.readString
                (Path.of(GlobalConstants.INPUT_FILES_PATH + GlobalConstants.ORDERS));

        OrderSeedDTO[] orderSeedDTOS = gson.fromJson
                (fileContent, OrderSeedDTO[].class);

//
//        List<Orders> ordersList = Arrays.stream(orderSeedDTOS)
//                .filter(validationUtil::isValid)
//                .map(orderSeedDTO -> {
//                    Orders order = modelMapper.map(orderSeedDTO, Orders.class);
//                    order.setCustomer(customerService.findRandomCustomer());
//                    order.setProducts(productService.findRandomProducts());
//                    return order;
//                })
//                                .collect(Collectors.toList());

        Arrays.stream(orderSeedDTOS)
                .filter(validationUtil::isValid)
                .map(orderSeedDTO -> {
                    Orders order = modelMapper.map(orderSeedDTO, Orders.class);
                    order.setCustomer(customerService.findRandomCustomer());
                    order.setProducts(productService.findRandomProducts());
                    return order;
                })
                .forEach(orderRepository::save);
    }
}
