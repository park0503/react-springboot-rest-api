package com.example.gccoffee.controller;

import com.example.gccoffee.domain.CreateProductRequest;
import com.example.gccoffee.domain.Product;
import com.example.gccoffee.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String productsPage(Model model) {
        List<Product> allProducts = productService.getAllProducts();
        model.addAttribute("products", allProducts);
        return "product-list";
    }

    @GetMapping("/new-product")
    public String NewProductPage() {
        return "new-product";
    }

    @PostMapping("/products")
    public String newProduct(CreateProductRequest createProductRequest) {
        productService.createProduct(createProductRequest.getProductName(), createProductRequest.getCategory(), createProductRequest.getPrice(), createProductRequest.getDescription());
        return "redirect:/products";
    }
}
