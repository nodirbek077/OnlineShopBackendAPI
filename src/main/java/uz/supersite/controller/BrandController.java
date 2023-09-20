package uz.supersite.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.supersite.entity.Brand;
import uz.supersite.service.BrandService;

import java.io.IOException;
import java.util.List;

@EnableMethodSecurity
@RestController
@RequestMapping("/v1/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @PreAuthorize("hasAnyAuthority('Admin','Editor')")
    @GetMapping()
    public ResponseEntity<?> getBrandsByPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        List<Brand> brandsList = brandService.getBrandsByPageable(page,size);
        return ResponseEntity.ok(brandsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id){
        Brand brands = brandService.get(id);
        return ResponseEntity.status(brands != null ? HttpStatus.OK : HttpStatus.CONFLICT).body(brands);
    }

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpEntity<?> add(@Valid Brand brand, MultipartFile file) throws IOException {
        Brand addedBrand = brandService.add(brand, file);
        return ResponseEntity.status(201).body(addedBrand);
    }

    @PutMapping(value = "/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpEntity<?> update(@Valid Brand brand, @RequestPart MultipartFile file, @PathVariable Integer id) throws IOException {
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
