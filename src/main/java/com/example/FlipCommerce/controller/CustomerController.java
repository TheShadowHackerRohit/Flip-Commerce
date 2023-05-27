package com.example.FlipCommerce.controller;


import com.example.FlipCommerce.dtos.RequestDTO.CustomerRequestDto;
import com.example.FlipCommerce.dtos.ResponseDTO.CustomerResponseDto;
import com.example.FlipCommerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {


    @Autowired
    CustomerService customerService;

    @PostMapping("/add-customer")
    public ResponseEntity addCustomer(@RequestBody CustomerRequestDto customerRequestDto){

        CustomerResponseDto customerResponseDto = customerService.addCustomer(customerRequestDto);

        return new ResponseEntity<>(customerRequestDto, HttpStatus.CREATED);

    }

    // get all female customers between age 20-30

    // get all male customers less than 45

    // customers who have ordered atleast 'k' orders

}
