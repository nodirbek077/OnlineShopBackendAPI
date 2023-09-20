package uz.supersite.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.supersite.entity.Category;
import uz.supersite.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getCategories(){
        List<Category> categories = categoryService.list();
        if(categories.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/root")
    public ResponseEntity<?> getRootCategories(){
        List<Category> categories = categoryService.getRootCategories();
        if(categories.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getCategory(@PathVariable Integer id){
        Category category = categoryService.get(id);
        return ResponseEntity.status(category != null ? HttpStatus.OK : HttpStatus.CONFLICT).body(category);
    }

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> addCategory(@Valid Category category, MultipartFile file){
        Category savedCategory = categoryService.add(category, file);
        return ResponseEntity.status(201).body(savedCategory);
    }

    @PutMapping(value = "/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @Valid Category category,@RequestPart MultipartFile file) {
        Category updateCategory = categoryService.update(category, file, id);
        return ResponseEntity.status(updateCategory != null ? 202 : 409).body(updateCategory);
    }
    @GetMapping("/{id}/enabled/{status}")
    public uz.supersite.ResponseEntity updateCategoryEnabledStatus(@PathVariable Integer id, @PathVariable boolean status){
        categoryService.updateCategoryEnabledStatus(id, status);
        return new uz.supersite.ResponseEntity("Status updated");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id){
        boolean deleted = categoryService.delete(id);
        if (deleted)
            return ResponseEntity.noContent().build();
        return ResponseEntity.noContent().build();
    }

}
