package uz.supersite.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.supersite.entity.Brand;
import uz.supersite.service.BrandService;

import java.util.List;
@RestController
@RequestMapping("/v1/brands")
public class BrandController {
    @Autowired
    private BrandService brandService;
    @GetMapping()
    public ResponseEntity<?> getBrandsByPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        List<Brand> brandsList = brandService.getNewsByPageable(page,size);
        return ResponseEntity.ok(brandsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id){
        Brand brands = brandService.get(id);
        return ResponseEntity.status(brands != null ? HttpStatus.OK : HttpStatus.CONFLICT).body(brands);
    }

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpEntity<?> add(@Valid Brand brand, MultipartFile file) {
        Brand addedBrand = brandService.add(brand, file);
        return ResponseEntity.status(201).body(addedBrand);
    }

    @PutMapping(value = "/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpEntity<?> update(@Valid Brand brand, @RequestPart MultipartFile file, @PathVariable Integer id){
        Brand editingBrand = brandService.updateBrand(id, brand, file);
        return ResponseEntity.status(editingBrand != null ? 202 : 409).body(editingBrand);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        boolean deleted = brandService.delete(id);
        if (deleted)
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
