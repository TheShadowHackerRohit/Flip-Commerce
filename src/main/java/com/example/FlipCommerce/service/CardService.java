package com.example.FlipCommerce.service;

import com.example.FlipCommerce.dtos.RequestDTO.CardRequestDto;
import com.example.FlipCommerce.dtos.ResponseDTO.CardResponseDto;

public interface CardService {

    public CardResponseDto addCard(CardRequestDto cardRequestDto);

}
