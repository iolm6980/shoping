package com.example.shoping.dto;

import com.example.shoping.entity.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page;
    private int size;
    private ProductType productType;
    private String type;
    private String keyword;

    public PageRequestDTO() {
        this.page = 1;
        this.size = 20;
    }
    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page -1, size, sort);
    }
    public Pageable getPageable() {
        return PageRequest.of(page -1, size);
    }


}