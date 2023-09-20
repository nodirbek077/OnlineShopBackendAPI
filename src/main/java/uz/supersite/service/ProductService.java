package uz.supersite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.supersite.entity.Product;
import uz.supersite.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    @Autowired private ProductRepository productRepository;

    public List<Product> listAll(){
        return (List<Product>) productRepository.findAll();
    }
}
