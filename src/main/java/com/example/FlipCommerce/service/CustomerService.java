package com.example.FlipCommerce.service;

import com.example.FlipCommerce.Enum.Gender;
import com.example.FlipCommerce.dto.RequestDto.CustomerRequestDto;
import com.example.FlipCommerce.dto.ResponseDto.CustomerResponseDto;
import com.example.FlipCommerce.dto.ResponseDto.ItemResponseDto;
import com.example.FlipCommerce.dto.ResponseDto.OrderResponseDto;
import com.example.FlipCommerce.exception.CustomerNotFoundException;
import com.example.FlipCommerce.model.Cart;
import com.example.FlipCommerce.model.Customer;
import com.example.FlipCommerce.model.OrderEntity;
import com.example.FlipCommerce.repository.CustomerRepository;
import com.example.FlipCommerce.repository.OrderRepository;
import com.example.FlipCommerce.transformer.CustomerTransformer;
import com.example.FlipCommerce.transformer.OrderTransformer;
import org.apache.naming.factory.SendMailFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    JavaMailSender mailSender;

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

        // send mail.
        sendMail(customer.getEmailId(), "Hello "+customer.getName()+". Welcome to FlipCommerce.");

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
            if(cus.getGender() == Gender.FEMALE && cus.getAge() >= from && cus.getAge() <= to){
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
    public List<List<ItemResponseDto>> ordersPlaced(String email) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByEmailId(email);

        if(customer==null){
            throw new CustomerNotFoundException("Customer Not Registered!");
        }

        List<OrderEntity> orderEntities =  orderRepository.orderOfCustomer(customer.getId());

        List<List<ItemResponseDto>> orderResponseDtoList = new ArrayList<>();

        for(OrderEntity curr : orderEntities){
            orderResponseDtoList.add(OrderTransformer.OrderToOrderResponseDto(curr).getItems());
        }

        return orderResponseDtoList;
    }

    public void sendMail(String toEmail, String text){

        Customer cus = customerRepository.findByEmailId(toEmail);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bookingvaccine3@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Account Created!");
        message.setText(text);
        mailSender.send(message);

    }
}