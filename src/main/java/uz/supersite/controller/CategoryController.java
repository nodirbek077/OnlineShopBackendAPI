package uz.supersite.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.supersite.entity.Category;
import uz.supersite.entity.Role;
import uz.supersite.exception.CategoryNotFoundException;
import uz.supersite.exception.RoleNotFoundException;
import uz.supersite.service.CategoryService;
import uz.supersite.service.RoleService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestBody @Valid Category category){
        Category savedCategory = categoryService.add(category);
        URI uri = URI.create("/v1/categories/" + savedCategory.getId());
        return ResponseEntity.created(uri).body(savedCategory);
    }

    @GetMapping
    public ResponseEntity<?> getCategories(){
        List<Category> categories = categoryService.list();

        if(categories.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(categories);
    }


    @GetMapping("/root")
    public ResponseEntity<?> getRootCategories(){
        List<Category> categories = categoryService.getRootCategories();

        if(categories.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(categories);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable("id") Integer id) throws CategoryNotFoundException {
        Category category = categoryService.get(id);
        if (category == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody @Valid Category category, @PathVariable("id") Integer id){
        try {
            Category updateCategory = categoryService.update(category, id);
            return ResponseEntity.ok(updateCategory);
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer id){
        try {
            categoryService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
