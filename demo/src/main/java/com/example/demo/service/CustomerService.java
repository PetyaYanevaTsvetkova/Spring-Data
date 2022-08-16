package com.example.demo.service;

import com.example.demo.model.entity.Customer;

import java.io.IOException;

public interface CustomerService {
    void seedCustomersData() throws IOException;

    Customer findRandomCustomer();
}
