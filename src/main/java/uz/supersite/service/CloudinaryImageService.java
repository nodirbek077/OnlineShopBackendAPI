package uz.supersite.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryImageService {
    String upload(MultipartFile file);
}
