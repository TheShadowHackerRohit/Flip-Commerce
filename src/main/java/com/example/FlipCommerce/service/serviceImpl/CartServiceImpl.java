package com.example.FlipCommerce.service.serviceImpl;

import com.example.FlipCommerce.dtos.RequestDTO.ItemRequestDto;
import com.example.FlipCommerce.dtos.ResponseDTO.CartResponseDto;
import com.example.FlipCommerce.model.Cart;
import com.example.FlipCommerce.model.Customer;
import com.example.FlipCommerce.model.Item;
import com.example.FlipCommerce.model.Product;
import com.example.FlipCommerce.repository.CartRepository;
import com.example.FlipCommerce.repository.CustomerRepository;
import com.example.FlipCommerce.repository.ProductRepository;
import com.example.FlipCommerce.service.CartService;
import com.example.FlipCommerce.transformer.CartTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public CartResponseDto addCart(Item item, ItemRequestDto itemRequestDto) {


        Customer customer = customerRepository.findByEmailId(itemRequestDto.getCustomerEmailId());
        Product product = productRepository.findById(itemRequestDto.getProductId()).get();

        Cart cart = customer.getCart();

        cart.setCartTotal(cart.getCartTotal()+ item.getRequiredQuantity()*product.getPrice());
        cart.getItems().add(item);
        item.setCart(cart);
        item.setProduct(product);


        Cart savedCart = cartRepository.save(cart);
        Item savedItem = cart.getItems().get(cart.getItems().size()-1);
        product.getItems().add(savedItem);

        //prepare request dto

        CartResponseDto cartResponseDto = CartTransformer.CartToCartResponseDto(savedCart);
        return cartResponseDto;
    }
}
