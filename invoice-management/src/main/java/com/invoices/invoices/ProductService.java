package com.invoices.invoices;

import com.invoices.invoices.domain.Product;
import com.invoices.invoices.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;

@Service
@RequestMapping("/product")
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @PostMapping("/add")
    public String addProduct(ProductDto product) {

        productRepository.save(Product.builder()
                .name(product.getName())
                .attributes(product.getAttributes())
                .code(product.getCode())
                .description(product.getDescription())
                .discount(product.getDiscount())
                .price(product.getPrice())
                .build());

        return "ok";
    }
}
