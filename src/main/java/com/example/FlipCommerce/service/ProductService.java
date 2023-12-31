package com.example.FlipCommerce.service;

import com.example.FlipCommerce.Enum.Category;
import com.example.FlipCommerce.dto.RequestDto.ProductRequestDto;
import com.example.FlipCommerce.dto.ResponseDto.ProductResponseDto;
import com.example.FlipCommerce.exception.SellerNotFoundException;
import com.example.FlipCommerce.model.Product;
import com.example.FlipCommerce.model.Seller;
import com.example.FlipCommerce.repository.ProductRepository;
import com.example.FlipCommerce.repository.SellerRepository;
import com.example.FlipCommerce.repository.Specifications.ProductSpecs;
import com.example.FlipCommerce.transformer.ProductTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.FlipCommerce.repository.Specifications.ProductSpecs.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    private ProductRepository productRepository;

    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws SellerNotFoundException {

        Seller seller = sellerRepository.findByEmailId(productRequestDto.getSellerEmailId());
        if(seller==null){
            throw new SellerNotFoundException("Email Id is not registered");
        }
        // dto to entity
        Product product = ProductTransformer.ProductRequestDtoToProduct(productRequestDto);
        seller.getProducts().add(product);
        product.setSeller(seller);


        // save product
        Seller savedSeller = sellerRepository.save(seller); // save both product and seller
        //Product savedProduct = savedSeller.getProducts().get(savedSeller.getProducts().size()-1);

        // prepare response dto
        return ProductTransformer.ProducToProductResponseDto(product);
    }

    public List<ProductResponseDto> getAllProductsByCategoryAndPrice(Category category, int price){

        List<Product> products = productRepository.findByCategoryAndPrice(category,price);

        // prepare a list of dtos
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product: products){
            productResponseDtos.add(ProductTransformer.ProducToProductResponseDto(product));
        }
        return productResponseDtos;
    }

    public List<ProductResponseDto> getAllProductsByCategory(Category category) {
        List<Product> products = productRepository.findByCategory(category);

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for(Product product : products){
            productResponseDtoList.add(ProductTransformer.ProducToProductResponseDto(product));
        }

        return productResponseDtoList;
    }

    public List<ProductResponseDto> getProducts(String productQuery) {

        List<Product> products = productRepository.findAll(new ProductSpecs().getProductsByName(productQuery));

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for(Product product : products){
            productResponseDtoList.add(ProductTransformer.ProducToProductResponseDto(product));
        }

        return productResponseDtoList;
    }

    public List<ProductResponseDto> getCategoryAndPriceGreater(Category category, int price) {

        List<Product> products = productRepository.findAll(new ProductSpecs().getCategoryAndPriceGreater(category, price));

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for(Product product : products){
            productResponseDtoList.add(ProductTransformer.ProducToProductResponseDto(product));
        }

        return productResponseDtoList;
    }
}
