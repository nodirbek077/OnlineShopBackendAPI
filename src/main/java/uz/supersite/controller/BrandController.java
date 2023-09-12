package uz.supersite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.supersite.entity.Brand;
import uz.supersite.service.BrandService;

import java.util.List;
@RestController
@RequestMapping("/v1/brands")
public class BrandController {
    private final BrandService brandService;
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping()
    public ResponseEntity<?> getBrandsByPage(){
        List<Brand> brands = brandService.getAll();
        return ResponseEntity.ok(brands);
    }
}
