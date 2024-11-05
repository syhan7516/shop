package com.shop.backend.service;

import com.shop.backend.dto.ProductMypriceRequestDto;
import com.shop.backend.dto.ProductRequestDto;
import com.shop.backend.dto.ProductResponseDto;
import com.shop.backend.entity.Product;
import com.shop.backend.entity.User;
import com.shop.backend.entity.UserRoleEnum;
import com.shop.backend.naver.dto.ItemDto;
import com.shop.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    public Page<ProductResponseDto> getProduct(User user, int page, int size, String sortBy, boolean isAsc) {
        // 페이징 처리를 위한 Pageable 객체 생성
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // 유저 권한 확인
        UserRoleEnum userRoleEnum = user.getRole();
        Page<Product> productList;
        if(userRoleEnum == UserRoleEnum.USER){
            productList = productRepository.findAllByUser(user, pageable);
        }else{
            productList = productRepository.findAll(pageable);
        }

        return productList.map(ProductResponseDto::new);
    }

    @Transactional
    public void updateBySearch(Long id, ItemDto itemDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NullPointerException("해당 상품을 존재하지 않습니다."));
        product.updateByItemDto(itemDto);
    }
}