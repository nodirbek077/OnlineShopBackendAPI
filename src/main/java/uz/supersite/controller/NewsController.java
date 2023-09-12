package uz.supersite.controller;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.supersite.entity.News;
import uz.supersite.exception.ItemNotFoundException;
import uz.supersite.service.NewsService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/news")
public class NewsController {
    private final NewsService newsService;
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public uz.supersite.ResponseEntity add(@RequestPart("news") @Valid String news, @RequestPart("file") MultipartFile file) throws IOException {
        newsService.add(news, file);
        return new uz.supersite.ResponseEntity(0, "News added successfully");
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<News> news = newsService.getAll();
        if(news.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(news);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer id) throws ItemNotFoundException {
        News news = newsService.get(id);
        if (news == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(news);
    }

    @PutMapping(value = "/update/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public uz.supersite.ResponseEntity update(@RequestPart("news") @Valid String news,
                                              @RequestPart("file") MultipartFile file,
                                              @PathVariable("id") Integer id) throws IOException, ItemNotFoundException {
            newsService.update(news, file, id);
            return new uz.supersite.ResponseEntity(0, "News updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        try {
            newsService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
