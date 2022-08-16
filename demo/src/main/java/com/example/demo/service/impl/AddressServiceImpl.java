package com.example.demo.service.impl;

import com.example.demo.constants.GlobalConstants;
import com.example.demo.model.dto.AddressSeedDTO;
import com.example.demo.model.entity.Address;
import com.example.demo.repository.AddressRepository;
import com.example.demo.service.AddressService;
import com.example.demo.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public AddressServiceImpl(AddressRepository addressRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedAddressData() throws IOException {
        if (addressRepository.count() > 0) {
            return;
        }

        String fileContent = Files.readString(Path.of(GlobalConstants.INPUT_FILES_PATH + GlobalConstants.ADDRESS));

        AddressSeedDTO[] addressSeedDTOS = gson
                .fromJson(fileContent, AddressSeedDTO[].class);

        Arrays.stream(addressSeedDTOS)
                .filter(validationUtil::isValid)
                .map(addressSeedDTO -> modelMapper.map(addressSeedDTO, Address.class))
                .forEach(addressRepository::save);
    }

    @Override
    public Address findRandomAddress() {

        long randomId = ThreadLocalRandom
                .current().nextLong(1, addressRepository.count() + 1);

        return addressRepository.findById(randomId).orElse(null);
    }
}
