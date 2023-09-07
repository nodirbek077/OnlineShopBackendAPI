package uz.supersite.service;

import org.springframework.web.multipart.MultipartFile;
import uz.supersite.entity.Attachment;

import java.io.IOException;

public interface AttachmentService {
    Attachment saveAttachment(MultipartFile file) throws Exception;

    Attachment getAttachment(String fileId) throws Exception;

    byte[] downloadImage(String fileName);

    String uploadImage(MultipartFile file) throws IOException;
}
