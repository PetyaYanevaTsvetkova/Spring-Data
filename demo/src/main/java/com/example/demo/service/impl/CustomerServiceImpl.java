package com.example.demo.service.impl;
import com.example.demo.constants.GlobalConstants;
import com.example.demo.model.dto.CustomerSeedDTO;
import com.example.demo.model.entity.Customer;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.AddressService;
import com.example.demo.service.CustomerService;
import com.example.demo.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressService addressService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public CustomerServiceImpl(CustomerRepository customerRepository, AddressRepository addressRepository, AddressService addressService, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.customerRepository = customerRepository;
        this.addressService = addressService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public void seedCustomersData() throws IOException {
        if (customerRepository.count() > 0) {
            return;
        }

        //json as String:
        String fileContent = Files
                .readString(Path.of(GlobalConstants.INPUT_FILES_PATH + GlobalConstants.CUSTOMER));

        //save as User DTOs Array:
        CustomerSeedDTO[] customerSeedDTOS = gson
                .fromJson(fileContent, CustomerSeedDTO[].class);

        List<Customer> customers = Arrays.stream(customerSeedDTOS)
                .filter(validationUtil::isValid)
                .map(customerSeedDTO -> {
                    Customer customer = modelMapper.map(customerSeedDTO, Customer.class);
                    customer.setAddress(addressService.findRandomAddress());
                    return customer;
                })
                .collect(Collectors.toList());


        //mapping to POJOs, validate and save entities in Database:
        Arrays.stream(customerSeedDTOS)
                .filter(validationUtil::isValid)
                .map(customerSeedDTO -> {
                    Customer customer = modelMapper.map(customerSeedDTO, Customer.class);
                    customer.setAddress(addressService.findRandomAddress());
                    return customer;
                })
                .forEach(customerRepository::save);
    }

    @Override
    public Customer findRandomCustomer() {
        Long randomId = ThreadLocalRandom.current().nextLong(1, customerRepository.count() + 1);
        return customerRepository.findById(randomId).orElse(null);
    }
}
