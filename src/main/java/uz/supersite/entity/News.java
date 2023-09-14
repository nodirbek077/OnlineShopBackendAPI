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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    @JsonIgnore
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
}
