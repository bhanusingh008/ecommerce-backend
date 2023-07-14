package com.example.FlipCommerce.service;

import com.example.FlipCommerce.Enum.Gender;
import com.example.FlipCommerce.dto.RequestDto.CustomerRequestDto;
import com.example.FlipCommerce.dto.ResponseDto.CustomerResponseDto;
import com.example.FlipCommerce.model.Cart;
import com.example.FlipCommerce.model.Customer;
import com.example.FlipCommerce.repository.CustomerRepository;
import com.example.FlipCommerce.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto){

        // dto -> entity
        Customer customer = CustomerTransformer.CustomerRequestDtoToCustomer(customerRequestDto);
        Cart cart = Cart.builder()
                .cartTotal(0)
                .customer(customer)
                .build();

        customer.setCart(cart);

        Customer savedCustomer = customerRepository.save(customer);  // saves both customer and cart
        // prepare response Dto
        return CustomerTransformer.CustomerToCustomerResponseDto(savedCustomer);

    }

    public CustomerResponseDto deleteCustomer(String email){

        // write sql to delete the customer from the database.

        Customer cus = customerRepository.findByEmailId(email);

        customerRepository.delete(cus);

        return CustomerTransformer.CustomerToCustomerResponseDto(cus);
    }

    public int getFemaleCustomerInRange(int from, int to) {
        int total = 0;
        List<Customer> list = customerRepository.findAll();

        for(Customer cus : list){
            if(cus.getGender() == Gender.FEMALE){
                total++;
            }
        }
        return total;
    }

    public List<CustomerResponseDto> getAllUserUnderSomeAge(int limit) {
        List<Customer> list = customerRepository.findAll();

        List<CustomerResponseDto> req = new ArrayList<>();

        for(Customer cus : list){
            if(cus.getAge() <= limit){
                CustomerResponseDto responseDto =  CustomerTransformer.CustomerToCustomerResponseDto(cus);
                req.add(responseDto);
            }
        }
        return req;
    }
}