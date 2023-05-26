package com.example.FlipCommerce.service.serviceImpl;

import com.example.FlipCommerce.dtos.RequestDTO.SellerRequestDto;
import com.example.FlipCommerce.dtos.ResponseDTO.SellerResponseDto;
import com.example.FlipCommerce.model.Seller;
import com.example.FlipCommerce.repository.SellerRepository;
import com.example.FlipCommerce.service.SellerService;
import com.example.FlipCommerce.transformer.SellerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl  implements SellerService {

    @Autowired
    SellerRepository sellerRepository;

    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto){
        //request dto to entity
        Seller seller = SellerTransformer.SellerRequestDtoToSeller(sellerRequestDto);

        //saved the seller
        Seller savedSeller = sellerRepository.save(seller);

        //seller to seller response dto
        SellerResponseDto sellerResponseDto = SellerTransformer.SellerToSellerResponseDto(savedSeller);

        return sellerResponseDto;
    }
}
