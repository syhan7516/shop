package com.shop.backend.controller;

import com.shop.backend.dto.ProductRequestDto;
import com.shop.backend.dto.ProductResponseDto;
import com.shop.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto){

        return productService.createProduct(requestDto);
    }
}