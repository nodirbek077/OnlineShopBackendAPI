package uz.supersite.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.supersite.entity.Attachment;
import uz.supersite.repository.AttachmentRepository;
import uz.supersite.utils.ImageUtil;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
public class AttachmentServiceImpl implements AttachmentService{
    private final AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository){
        this.attachmentRepository = attachmentRepository;
    }
    @Override
    public Attachment saveAttachment(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            if(fileName.contains("..")){
                throw new Exception("Filename contains invalid path sequence" + fileName);
            }
            Attachment attachment = new Attachment(
                    fileName,
                    file.getContentType(),
                    file.getBytes()
            );
            return attachmentRepository.save(attachment);
        } catch (Exception e) {
            throw new Exception("Could not save file: " + fileName);
        }
    }

    @Override
    public Attachment getAttachment(String fileId) throws Exception {
        return attachmentRepository.findById(fileId)
                .orElseThrow(() -> new Exception("File not found with id: " + fileId));
    }

    public byte[] downloadImage(String fileName){
        Optional<Attachment> dbImageData = attachmentRepository.findByFileName(fileName);
        if(dbImageData.isPresent()){
            Attachment attachment = dbImageData.get();
            byte[] data = attachment.getData();
            return ImageUtil.decompressImage(data);
        }
        return new byte[0];
    }

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        Attachment attachment = new Attachment();
        attachment.setFileName(file.getOriginalFilename());
        attachment.setFileType(file.getContentType());
        attachment.setData(ImageUtil.compressImage(file.getBytes()));

        attachmentRepository.save(attachment);
        return "file uploaded successfully: " + file.getOriginalFilename();
    }
}
