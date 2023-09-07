package uz.supersite.controller.base;

import jakarta.validation.Valid;
import org.apache.catalina.LifecycleState;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.supersite.entity.News;

import java.io.IOException;

public interface BaseController <T> {
     ResponseEntity<T> add(T t);

     ResponseEntity<?> getAll();


}
