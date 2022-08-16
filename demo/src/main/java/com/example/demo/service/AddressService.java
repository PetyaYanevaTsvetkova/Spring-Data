package com.example.demo.service;

import com.example.demo.model.entity.Address;

import java.io.IOException;

public interface AddressService {
    void seedAddressData() throws IOException;

    Address findRandomAddress();
}
