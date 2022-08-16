package com.example.demo;
import com.example.demo.service.AddressService;
import com.example.demo.service.CustomerService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final CustomerService customerService;
    private final AddressService addressService;
    private final ProductService productService;
    private final OrderService orderService;

    public CommandLineRunnerImpl(CustomerService customerService, AddressService addressService, ProductService productService, OrderService orderService) {
        this.customerService = customerService;
        this.addressService = addressService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();


    }


    
    private void seedData() throws IOException {
        addressService.seedAddressData();
        customerService.seedCustomersData();
        productService.seedProductData();
        orderService.seedOrderDate();
    }
}
