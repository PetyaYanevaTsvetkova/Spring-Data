package com.example.demo;

import com.example.demo.model.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DemoTest1ApplicationTests {
    private final CustomerRepository customerRepository;

    DemoTest1ApplicationTests(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void printAllCustomersNames() {
        List<Customer> collect = new ArrayList<>(customerRepository.findAll());
        System.out.println();
//		for (Customer customer : collect) {
//			System.out.println(customer.getFirstName());
    }
}


