package com.example.FlipCommerce.service;

import com.example.FlipCommerce.dtos.RequestDTO.SellerRequestDto;
import com.example.FlipCommerce.dtos.ResponseDTO.SellerResponseDto;
import com.example.FlipCommerce.model.Product;

import java.util.List;

public interface SellerService {

    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto);


}
