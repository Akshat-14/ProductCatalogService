package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.models.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@Controller
@RestController
public class ProductController {
    //Product @ResponseBody getProductById(Long id){}
    @GetMapping("/products/{id}")
    Product getProductById(@PathVariable("id") Long productId) {
        Product product = new Product();
        product.setId(productId);
        return product;
    }

    @PostMapping("/products")
    Product createProduct(@RequestBody Product input) {
        return input;
    }
}
