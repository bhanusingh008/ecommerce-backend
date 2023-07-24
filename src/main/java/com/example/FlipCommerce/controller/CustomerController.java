package com.example.FlipCommerce.controller;

import com.example.FlipCommerce.dto.RequestDto.CustomerRequestDto;
import com.example.FlipCommerce.dto.ResponseDto.CustomerResponseDto;
import com.example.FlipCommerce.dto.ResponseDto.ItemResponseDto;
import com.example.FlipCommerce.dto.ResponseDto.OrderResponseDto;
import com.example.FlipCommerce.exception.CustomerNotFoundException;
import com.example.FlipCommerce.model.Customer;
import com.example.FlipCommerce.model.OrderEntity;
import com.example.FlipCommerce.service.CustomerService;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    // give a list of list so as to distinguish between two different order entities.
    @GetMapping("/orders-placed")
    public ResponseEntity ordersPlaced(@RequestParam("email") String email) throws CustomerNotFoundException {
        try{
            List<List<ItemResponseDto>> ordersList = customerService.ordersPlaced(email);

            return new ResponseEntity<>(ordersList, HttpStatus.FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}