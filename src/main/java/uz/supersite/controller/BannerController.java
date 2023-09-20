package uz.supersite.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.supersite.entity.Banner;
import uz.supersite.entity.Brand;
import uz.supersite.service.BannerService;
import uz.supersite.service.BrandService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/banners")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping()
    public ResponseEntity<?> getBannersByPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        List<Banner> brandsList = bannerService.getBannerByPageable(page,size);
        return ResponseEntity.ok(brandsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id){
        Banner banner = bannerService.get(id);
        return ResponseEntity.status(banner != null ? HttpStatus.OK : HttpStatus.CONFLICT).body(banner);
    }

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpEntity<?> add(@Valid Banner banner, MultipartFile file) throws IOException {
        Banner addedBanner = bannerService.add(banner, file);
        return ResponseEntity.status(201).body(addedBanner);
    }

    @PutMapping(value = "/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpEntity<?> update(@Valid Banner banner, @RequestPart MultipartFile file, @PathVariable Integer id) throws IOException {
        Banner editingBanner = bannerService.updateBanner(id, banner, file);
        return ResponseEntity.status(editingBanner != null ? 202 : 409).body(editingBanner);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        boolean deleted = bannerService.delete(id);
        if (deleted)
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
