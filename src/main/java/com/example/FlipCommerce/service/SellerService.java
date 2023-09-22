package com.example.FlipCommerce.service;

import com.example.FlipCommerce.dto.RequestDto.SellerRequestDto;
import com.example.FlipCommerce.dto.ResponseDto.SellerResponseDto;
import com.example.FlipCommerce.model.Seller;
import com.example.FlipCommerce.repository.SellerRepository;
import com.example.FlipCommerce.transformer.SellerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;

    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto){

        // dto -> entity
        Seller seller = SellerTransformer.SellerRequestDtoToSeller(sellerRequestDto);
        Seller savedSeller = sellerRepository.save(seller);
        // prepare response Dto
        return SellerTransformer.SellerToSellerResponseDto(savedSeller);
    }

    public List<SellerResponseDto> getAllSellers() {

        List<Seller> sellerList = sellerRepository.findAll();

        List<SellerResponseDto> responseDto = new ArrayList<>();

        for(Seller seller : sellerList){
            responseDto.add(SellerTransformer.SellerToSellerResponseDto(seller));
        }

        return responseDto;
    }

    public SellerResponseDto getWithMostProducts() {
        return SellerTransformer.SellerToSellerResponseDto(sellerRepository.oneWithMostProducts());
    }
}
