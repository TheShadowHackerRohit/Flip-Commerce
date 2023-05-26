package com.example.FlipCommerce.repository;

import com.example.FlipCommerce.Enum.Category;
import com.example.FlipCommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findByCategoryAndPrice(Category category,int price);

}
