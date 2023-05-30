package com.example.FlipCommerce.service;

import com.example.FlipCommerce.dtos.RequestDTO.OrderRequestDto;
import com.example.FlipCommerce.dtos.ResponseDTO.OrderResponseDto;
import com.example.FlipCommerce.model.Card;
import com.example.FlipCommerce.model.Cart;
import com.example.FlipCommerce.model.OrderEntity;

import java.util.List;

public interface OrderService {

    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto);

    public OrderEntity placeOrder(Cart cart , Card card);

}
