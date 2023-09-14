package uz.supersite.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.supersite.entity.News;
import uz.supersite.entity.Vacancy;
import uz.supersite.exception.ItemNotFoundException;
import uz.supersite.service.NewsService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping()
    public ResponseEntity<?> getNewsByPage(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size){
        List<News> newsList = newsService.getNewsByPageable(page,size);
        return ResponseEntity.ok(newsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id){
        News news = newsService.get(id);
        return ResponseEntity.status(news != null ? HttpStatus.OK : HttpStatus.CONFLICT).body(news);
    }

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpEntity<?> add(@Valid News news, MultipartFile file) {
        News addedNews = newsService.add(news, file);
        return ResponseEntity.status(201).body(addedNews);
    }

    @PutMapping(value = "/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpEntity<?> update(@Valid News news, @RequestPart MultipartFile file, @PathVariable Integer id){
        News editingNews = newsService.updateNews(id, news, file);
        return ResponseEntity.status(editingNews != null ? 202 : 409).body(editingNews);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        boolean deleted = newsService.delete(id);
        if (deleted)
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
