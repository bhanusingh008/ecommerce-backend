package com.example.FlipCommerce.repository;

import com.example.FlipCommerce.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    @Query(value = "select * from order_info where customer_id = :id", nativeQuery = true)
    List<OrderEntity> orderOfCustomer(int id);
}
