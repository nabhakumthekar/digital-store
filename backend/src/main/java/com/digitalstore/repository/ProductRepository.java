package com.digitalstore.repository;

import com.digitalstore.model.Product;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Page<Product> findByCategoryContainingIgnoreCase(String category, Pageable pageable);
}
