package com.example.shoping.service;

import com.example.shoping.dto.PageRequestDTO;
import com.example.shoping.dto.PageResultDTO;
import com.example.shoping.dto.ProductDTO;
import com.example.shoping.entity.*;
import com.example.shoping.repository.CartRepository;
import com.example.shoping.repository.ProductImageRepository;
import com.example.shoping.repository.ProductRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final CartRepository cartRepository;

    @Override
    public PageResultDTO<ProductDTO, Object[]> getProductList(PageRequestDTO pageRequestDTO) {
        Page<Object[]> result = productRepository.searchPage(pageRequestDTO);
        Function<Object[], ProductDTO> fn = (arr -> entityToDTO((Product)arr[0], Arrays.asList((ProductImage) arr[1])));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public ProductDTO getProduct(Long pno) {
        List<Object[]> result = productRepository.getProduct(pno);
        Product product = (Product) result.get(0)[0];
        List<ProductImage> productImageList = new ArrayList<>();
        result.forEach(arr ->{
            ProductImage productImage = (ProductImage) arr[1];
            productImageList.add(productImage);
        });
        return entityToDTO(product, productImageList);
    }

    @Override
    public void removeProduct(Long pno) {
        productImageRepository.deleteByPno(pno);
        cartRepository.deleteByPno(pno);
        productRepository.deleteById(pno);
    }

    @Override
    public void modifyProduct(ProductDTO productDTO) {
        Product product = dtoToEntity(productDTO);
        productRepository.save(product);
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){
        ProductType type = requestDTO.getProductType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(type != null) booleanBuilder.and(QProduct.product.type.eq(type));

        return booleanBuilder;
    }
    @Override
    public List<Product> test(){
        Product product = Product.builder()
                .name("testProduct4")
                .build();
        ExampleMatcher e = ExampleMatcher.matchingAll()
                .withIgnorePaths("pno")
                .withIgnorePaths("likeCount")
                .withIgnorePaths("price");
        List<Product> result = productRepository.findAll(Example.of(product, e));
        return result;
    }



}
