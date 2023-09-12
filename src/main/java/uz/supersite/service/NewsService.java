package uz.supersite.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.supersite.entity.Attachment;
import uz.supersite.entity.News;
import uz.supersite.exception.ItemNotFoundException;
import uz.supersite.repository.AttachmentRepository;
import uz.supersite.repository.NewsRepository;
import uz.supersite.utils.FileUploadUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final AttachmentRepository attachmentRepository;

    public NewsService(NewsRepository newsRepository, AttachmentRepository attachmentRepository) {
        this.newsRepository = newsRepository;
        this.attachmentRepository = attachmentRepository;
    }

    public News add(String news, MultipartFile file) throws IOException {
        News newsJson = new News();
        Attachment attachment = new Attachment();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
             newsJson = objectMapper.readValue(news, News.class);

        }catch (IOException e){
            System.out.println("Error: " + e);
        }
        attachment.setFileType(file.getContentType());
//        attachment.setData(file.getBytes());
        attachment.setFileName(file.getOriginalFilename());
        Attachment savedAttachment = attachmentRepository.save(attachment);
//        newsJson.setAttachment(savedAttachment);
        return newsRepository.save(newsJson);
    }

    public List<News> getAll() {
        return (List<News>) newsRepository.findAll();
    }

    public News get(Integer id) throws ItemNotFoundException {
        Optional<News> optionalCategory = newsRepository.findById(id);
        if(optionalCategory.isPresent()){
            return optionalCategory.get();
        }else {
            throw new ItemNotFoundException("Category not found with id " + id);
        }
    }

    public News update(String newsInRequest, MultipartFile file, Integer id) throws ItemNotFoundException, IOException {
        Optional<News> newsInDb = newsRepository.findById(id);

        if(newsInDb.isEmpty()){
            throw new ItemNotFoundException("No category found with the given id: " + id);
        }

        News news = newsInDb.get();

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            news = objectMapper.readValue(newsInRequest, News.class);

        }catch (IOException e){
            System.out.println("Error: " + e);
        }

        Attachment attachment = new Attachment();
        attachment.setFileType(file.getContentType());
        attachment.setData(FileUploadUtil.compressImage(file.getBytes()));
        attachment.setFileName(file.getOriginalFilename());
        Attachment savedAttachment = attachmentRepository.save(attachment);

//        news.setAttachment(savedAttachment);
        news.setTitle(news.getTitle());
        news.setDescription(news.getDescription());
        news.setLink(news.getLink());
        news.setCreatedAt(news.getCreatedAt());

        return newsRepository.save(news);
    }

    public void delete(Integer id) throws ItemNotFoundException {
        if(!newsRepository.existsById(id)){
            throw new ItemNotFoundException("No news find with ID: " + id);
        }
        newsRepository.deleteById(id);
    }
}
