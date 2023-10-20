package com.tutorial.appdemo.Springboot.tutorial.repositories;

import com.tutorial.appdemo.Springboot.tutorial.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> { // Long: primary key's type
    List<Product> findByProductName(String productName);

}
