package com.example.FlipCommerce.repository;

import com.example.FlipCommerce.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {


}
