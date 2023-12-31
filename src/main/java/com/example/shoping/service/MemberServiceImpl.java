package com.example.shoping.service;

import com.example.shoping.dto.*;
import com.example.shoping.entity.Member;
import com.example.shoping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean memberRegister(MemberDTO memberDTO) {
        Optional<Member> result = memberRepository.findByUserId(memberDTO.getUserId());
        if(result.isPresent()) return false;
        Member member = dtoToEntity(memberDTO, passwordEncoder);
        memberRepository.save(member);
        return true;
    }

    @Override
    public MemberDTO getMember(String id) {
        Optional<Member> result = memberRepository.findByUserId(id);
        if(result.isEmpty()) return null;
        MemberDTO memberDTO = entityToDto(result.get());
        return memberDTO;
    }

    @Override
    public void modify(MemberDTO memberDTO) {
        Optional<Member> result = memberRepository.findByUserId(memberDTO.getUserId());
        Member member = result.get();
        member.setNickName(memberDTO.getNickName());
        member.setEmail(memberDTO.getEmail());
        member.setAddressNum(memberDTO.getAddressNum());
        member.setAddress(memberDTO.getAddress());
        member.setDetailAddress(memberDTO.getDetailAddress());
        member.setNumber(memberDTO.getNumber());
        memberRepository.save(member);
    }

//    @Override
//    public void addMyCart(ProductDTO productDTO, String userId) {
//        Optional<Member> result = memberRepository.findByUserId(userId);
//
//        Product product = dtoToEntity(productDTO);
//        MemberProduct memberProduct = MemberProduct.builder().member(result.get()).product(product).build();
//
//        if(result.isPresent()) memberProductRepository.save(memberProduct);
//
//    }
//
//
//    public PageResultDTO<ProductDTO, Product> getMyCart(String userId, PageRequestDTO pageRequestDTO) {
//        Optional<Member> member = memberRepository.findByUserId(userId);
//        List<Product> productList = memberProductRepository.getCartList(member.get());
//        Function<Product, ProductDTO> fn = (en -> entityToDTO(en));
//
//        int start = (pageRequestDTO.getPage()-1) * pageRequestDTO.getSize();
//        int end = start + pageRequestDTO.getSize() > productList.size() ? productList.size() : start + pageRequestDTO.getSize();
//
//        Page<Product> page = new PageImpl<>(productList.subList(start, end), pageRequestDTO.getPageable(), productList.size());
//        return new PageResultDTO<>(page, fn);
//    }

}
