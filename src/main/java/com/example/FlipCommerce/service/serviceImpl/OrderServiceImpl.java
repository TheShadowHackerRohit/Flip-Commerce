package com.example.FlipCommerce.service.serviceImpl;

import com.example.FlipCommerce.Enum.ProductStatus;
import com.example.FlipCommerce.Exception.CustomerNotFoundException;
import com.example.FlipCommerce.Exception.InsufficientQuantityException;
import com.example.FlipCommerce.Exception.InvalidCardException;
import com.example.FlipCommerce.Exception.ProductNotFoundException;
import com.example.FlipCommerce.dtos.RequestDTO.OrderRequestDto;
import com.example.FlipCommerce.dtos.ResponseDTO.OrderResponseDto;
import com.example.FlipCommerce.model.*;
import com.example.FlipCommerce.repository.*;
import com.example.FlipCommerce.service.OrderService;
import com.example.FlipCommerce.transformer.ItemTransformer;
import com.example.FlipCommerce.transformer.OrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {

        Customer customer = customerRepository.findByEmailId(orderRequestDto.getEmailId());
        if (customer == null){
            throw new CustomerNotFoundException("Customer Does not exist");
        }


        Optional<Product> optionalProduct = productRepository.findById(orderRequestDto.getProductId());

        if (optionalProduct.isEmpty()){
            throw  new ProductNotFoundException("Sorry! Product does not exist");
        }

        Product product = optionalProduct.get();
        //check quantity

        if (product.getQuantity() < orderRequestDto.getRequiredQuantity()){
            throw new InsufficientQuantityException("Required quantity is not available");
        }
//card check
        Card card = cardRepository.findByCardNo(orderRequestDto.getCardNo());
        Date date = new Date();
        if (card == null || card.getCvv() != orderRequestDto.getCvv()){// change the after to before here  || date.before(card.getValidTill())
            throw new InvalidCardException("Card is expired");
        }

        int newQuantity = product.getQuantity()-orderRequestDto.getRequiredQuantity();
        product.setQuantity(newQuantity);
        if (newQuantity == 0){
            product.setProductStatus(ProductStatus.OUT_OF_STOCK);
        }
        //Create the item

        Item item = ItemTransformer.ItemRequestDtoToItem(orderRequestDto.getRequiredQuantity());
        item.setProduct(product);

        // create order
        OrderEntity orderEntity = OrderTransformer.OrderRequestDtoToOrder(item,customer);
        String maskedCard = generateMaskedCardNO(card);
        orderEntity.setCardUsed(maskedCard);
        orderEntity.getItems().add(item);

        item.setOrderEntity(orderEntity);
        OrderEntity savedOrder = orderRepository.save(orderEntity);

        product.getItems().add(savedOrder.getItems().get(0));

        return OrderTransformer.OrderToOrderResponseDto(orderEntity);
    }

    public OrderEntity placeOrder(Cart cart , Card card){

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderNo(String.valueOf(UUID.randomUUID()));
        orderEntity.setCardUsed(generateMaskedCardNO(card));


        int totalValue = 0;
        for(Item item : cart.getItems()){
            Product product = item.getProduct();
            if (item.getRequiredQuantity() > product.getQuantity()){
                throw new InsufficientQuantityException("Required quantity not present!!");
            }
            totalValue += item.getRequiredQuantity()*product.getPrice();
            int newQuantity = product.getQuantity() -item.getRequiredQuantity();;
            product.setQuantity(newQuantity);
            if (newQuantity == 0){
                product.setProductStatus(ProductStatus.OUT_OF_STOCK);
            }
            item.setOrderEntity(orderEntity);
        }
        orderEntity.setTotalValue(totalValue);
        orderEntity.setItems(cart.getItems());
        orderEntity.setCustomer(cart.getCustomer());

        return orderEntity;
    }




    private String generateMaskedCardNO(Card card){
        String cardNo = "";
        String originalCardNo = card.getCardNo();
        for(int i = 0 ; i < card.getCardNo().length() ; i++){
            cardNo += "*";
        }
        cardNo += originalCardNo.substring(originalCardNo.length()-4);
        return cardNo;
    }
}
