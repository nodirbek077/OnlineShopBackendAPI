package uz.supersite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.supersite.entity.Product;
import uz.supersite.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    @Autowired private ProductService productService;
    @GetMapping
    public String listAll(){
        List<Product> products = productService.listAll();
        return products.toString();

    }
}
