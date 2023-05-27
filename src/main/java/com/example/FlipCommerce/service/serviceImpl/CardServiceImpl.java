package com.example.FlipCommerce.service.serviceImpl;

import com.example.FlipCommerce.Exception.CustomerNotFoundException;
import com.example.FlipCommerce.dtos.RequestDTO.CardRequestDto;
import com.example.FlipCommerce.dtos.ResponseDTO.CardResponseDto;
import com.example.FlipCommerce.model.Card;
import com.example.FlipCommerce.model.Customer;
import com.example.FlipCommerce.repository.CustomerRepository;
import com.example.FlipCommerce.service.CardService;
import com.example.FlipCommerce.transformer.CardTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public CardResponseDto addCard(CardRequestDto cardRequestDto) {

        Customer customer = customerRepository.findByEmailId(cardRequestDto.getEmailId());
        if(customer == null){
            throw new CustomerNotFoundException("Invalid Email Id");
        }
        //dto to entity

        Card card = CardTransformer.CardRequestDtoToCard(cardRequestDto);
        card.setCustomer(customer);

        customer.getCards().add(card);


        Customer savedCustomer = customerRepository.save(customer);//save both customer and card


        //prepare response dto

        CardResponseDto cardResponseDto = CardTransformer.CardToCardResponseDto(card);

        return cardResponseDto;






    }
}
