package uz.supersite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, length = 4096)
    private String description;

    @Column(unique = true)
    private String link;

    @Column(length = 128)
    private String image;

    @CreationTimestamp
    @Column(name = "created_by", nullable = false, updatable = false)
    private Date createdBy;

    @UpdateTimestamp
    @Column(name = "updated_by")
    private Date updatedBy;

    private boolean isActive;

    public News(String link){
        this.link = link;
    }

    public News(String title, String description, String image, boolean isActive) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        String domain = "https://api1.victoriaslove.uz/news";

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = dateFormat.format(date);

        this.link = domain + "/" + formattedDate + "/" + (this.title.contains("$") ? this.title.replace("$","") : this.title);
        this.link = this.link.toLowerCase().replace("'", "").replace(" ", "-");
        return link;
    }

    public void setLink(String link){
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Date createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Date updatedBy) {
        this.updatedBy = updatedBy;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public static void main(String[] args) {
        News news = new News(
                "Investment of nearly $1 billion spent in 5 years to improve the water supply system in Uzbekistan",
                "test description",
                "sdsdsdsd",
                true
                );

        System.out.println(news.getLink());
    }
}
