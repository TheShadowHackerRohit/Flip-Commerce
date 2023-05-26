package com.example.FlipCommerce.service;

import com.example.FlipCommerce.dtos.RequestDTO.CustomerRequestDto;
import com.example.FlipCommerce.dtos.ResponseDTO.CustomerResponseDto;

public interface CustomerService {

    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto);

}
