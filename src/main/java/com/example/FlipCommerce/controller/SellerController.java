package com.example.FlipCommerce.controller;

import com.example.FlipCommerce.dto.RequestDto.SellerRequestDto;
import com.example.FlipCommerce.dto.ResponseDto.SellerResponseDto;
import com.example.FlipCommerce.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @PostMapping("/add")
    public ResponseEntity addSeller(@RequestBody SellerRequestDto sellerRequestDto){

        SellerResponseDto sellerResponseDto = sellerService.addSeller(sellerRequestDto);
        return new ResponseEntity(sellerResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllSellers(){

        List<SellerResponseDto> list = sellerService.getAllSellers();

        return new ResponseEntity(list, HttpStatus.FOUND);
    }

    @GetMapping("/getWithMostProducts")
    public ResponseEntity getWithMostProducts(){

        SellerResponseDto sellerResponseDto = sellerService.getWithMostProducts();

        return new ResponseEntity<>(sellerResponseDto, HttpStatus.FOUND);
    }
    // update the seller info based on email.

    // get all the sellers who sell products of a particular category

    // get all the products sold by a seller in a category

    // seller with highest number of products

    // seller with minimum number of products

    // seller(s) selling the costliest product

    // seller(s) selling the cheapest product
}
