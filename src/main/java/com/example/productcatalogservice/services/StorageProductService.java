package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class StorageProductService implements IProductService{

    @Autowired
    private ProductRepo productRepo;

    @Override
    public Product getProductById(Long id) {
        Optional<Product> product = productRepo.findById(id);
        return product.orElse(null);
        //if (product.isEmpty()) return null;
        //else return product.get(); // Same as above
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        Optional<Product> optionalProduct = productRepo.findById(product.getId());
        // if (optionalProduct.isPresent()) return optionalProduct.get();
        // return productRepo.save(product);
        return optionalProduct.orElseGet(() -> productRepo.save(product));
    }

    @Override
    public Product replaceProduct(Product product, Long id) {
        return null;
    }
}
