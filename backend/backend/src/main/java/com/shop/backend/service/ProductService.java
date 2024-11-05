package com.shop.backend.service;

import com.shop.backend.dto.ProductMypriceRequestDto;
import com.shop.backend.dto.ProductRequestDto;
import com.shop.backend.dto.ProductResponseDto;
import com.shop.backend.entity.Product;
import com.shop.backend.entity.User;
import com.shop.backend.naver.dto.ItemDto;
import com.shop.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public static final int MIN_MY_PRICE = 100;

    public ProductResponseDto createProduct(ProductRequestDto requestDto,User user) {

        Product product = productRepository.save(new Product(requestDto,user));

        return new ProductResponseDto(product);
    }

    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductMypriceRequestDto requestDto) {

        int myprice = requestDto.getMyprice();

        if(myprice < MIN_MY_PRICE){
            throw new IllegalArgumentException("유효하지 않은 관심 가격입니다. 최소 " + MIN_MY_PRICE +"원 이상으로 설정해주세요.");
        }

        Product product = productRepository.findById(id).orElseThrow(() -> new NullPointerException("해당 상품을 찾을 수 없습니다."));
        product.update(requestDto);

        return new ProductResponseDto(product);
    }

    public List<ProductResponseDto> getProduct(User user) {

        List<Product> productList = productRepository.findAllByUser(user);
        List<ProductResponseDto> responseDtos = new ArrayList<>();

        for (Product product : productList) {
            responseDtos.add(new ProductResponseDto(product));
        }

        return responseDtos;
    }

    @Transactional
    public void updateBySearch(Long id, ItemDto itemDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NullPointerException("해당 상품을 존재하지 않습니다."));
        product.updateByItemDto(itemDto);
    }

    public List<ProductResponseDto> getAllProducts(){
        List<Product> productList = productRepository.findAll();
        List<ProductResponseDto> responseDtos = new ArrayList<>();
        for (Product product : productList) {
            responseDtos.add(new ProductResponseDto(product));
        }
        return responseDtos;
    }
}