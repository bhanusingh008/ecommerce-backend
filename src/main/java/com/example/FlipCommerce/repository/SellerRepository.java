package com.example.FlipCommerce.repository;

import com.example.FlipCommerce.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Integer> {
    Seller findByEmailId(String emailId);

    @Query(value = "select * from seller where seller.id " +
            "=" +
            " (select seller_id from (select seller_id, count(seller_id) as orders from product group by" +
            " seller_id order by orders desc limit 1) as tab);\n", nativeQuery = true)
    Seller oneWithMostProducts();
}
