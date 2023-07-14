package com.example.FlipCommerce.controller;

import com.example.FlipCommerce.dto.RequestDto.CustomerRequestDto;
import com.example.FlipCommerce.dto.ResponseDto.CustomerResponseDto;
import com.example.FlipCommerce.model.Customer;
import com.example.FlipCommerce.service.CustomerService;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity addCustomer(@RequestBody CustomerRequestDto customerRequestDto){

        CustomerResponseDto customerResponseDto = customerService.addCustomer(customerRequestDto);
        return new ResponseEntity(customerResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteCustomer(@RequestParam String email){

        CustomerResponseDto customerResponseDto = customerService.deleteCustomer(email);

        return new ResponseEntity(customerResponseDto, HttpStatus.ACCEPTED);
    }

    // get all female customers between some age interval

    @GetMapping("/get-female-between")
    public ResponseEntity getFemaleCustomerInRange(@RequestParam("from") int from, @RequestParam("to") int to){
        int num = customerService.getFemaleCustomerInRange(from, to);
        return new ResponseEntity<>(num, HttpStatus.FOUND);
    }

    // get all customers less than some age

    @GetMapping("/getMaleUnderLimit")
    public ResponseEntity getAllUserUnderSomeAge(@RequestParam("limit") int limit){
        List<CustomerResponseDto> list =  customerService.getAllUserUnderSomeAge(limit);
        return new ResponseEntity<>(list, HttpStatus.FOUND);
    }



}