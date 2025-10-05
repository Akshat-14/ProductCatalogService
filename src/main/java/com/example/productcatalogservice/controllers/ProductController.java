package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.CategoryDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@Controller
@RestController
public class ProductController {
    //Product @ResponseBody getProductById(Long id){}

    @Autowired
    //@Qualifier("fakeStoreProductService") . Can be added by removing primary in service
    private IProductService productService;

    @GetMapping("/products/{id}")
    ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        try {
            if(productId <= 0) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            Product product = productService.getProductById(productId);
            ProductDto productDto = from(product);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            return new ResponseEntity<>(productDto, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/products/{id}")
    ProductDto replaceProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product input = from(productDto);
        Product updatedProduct = productService.replaceProduct(input,id);
        return from(updatedProduct);
    }

    @PostMapping("/products")
    ProductDto createProduct(@RequestBody ProductDto input) {
        Product product = from(input);
        Product createdProduct = productService.createProduct(product);
        return from(createdProduct);
    }

    @GetMapping("/products")
    List<ProductDto> getAllProduct() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : products) {
            ProductDto productDto = from(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    private ProductDto from(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setId(product.getId());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setDescription(product.getCategory().getDescription());
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setId(product.getCategory().getId());
            productDto.setCategoryDto(categoryDto);
        }

        return productDto;
    }

    private Product from(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setDescription(productDto.getDescription());
        if(productDto.getCategoryDto() != null) {
            Category category = new Category();
            category.setId(productDto.getCategoryDto().getId());
            category.setName(productDto.getCategoryDto().getName());
            product.setCategory(category);
        }
        return product;
    }
}
