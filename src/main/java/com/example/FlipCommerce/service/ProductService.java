package com.example.FlipCommerce.service;

import com.example.FlipCommerce.Enum.Category;
import com.example.FlipCommerce.dtos.RequestDTO.ProductRequestDto;
import com.example.FlipCommerce.dtos.ResponseDTO.ProductResponseDto;
import com.example.FlipCommerce.model.Product;

import java.util.List;

public interface ProductService {

    public ProductResponseDto addSeller(ProductRequestDto productRequestDto);

    public List<ProductResponseDto> getAllProductsByCategoryAndPrice(Category category,int price);
}
