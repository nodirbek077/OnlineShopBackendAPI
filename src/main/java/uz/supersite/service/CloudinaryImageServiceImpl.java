package uz.supersite.service;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class CloudinaryImageServiceImpl implements CloudinaryImageService{
    @Autowired
    private Cloudinary cloudinary;
    @Override
    public String upload(MultipartFile file) {
        try {
            return  this.cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of("public_id", UUID.randomUUID().toString()))
                    .get("url")
                    .toString();

            //user--> cloudinary image url, public id, save

        } catch (IOException e) {
            throw new RuntimeException("Imaging upload fail!");
        }
    }
}
