package com.shop.backend.service;

import com.shop.backend.dto.ProductRequestDto;
import com.shop.backend.dto.ProductResponseDto;
import com.shop.backend.entity.Product;
import com.shop.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(ProductRequestDto requestDto) {

        Product product = productRepository.save(new Product(requestDto));

        return new ProductResponseDto(product);
    }
}