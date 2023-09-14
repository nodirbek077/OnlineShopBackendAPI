package uz.supersite.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "banners")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String subtitle;
    @Column(nullable = false, length = 128)
    private String image;

    public Banner(){
    }

    public Banner(Integer id, String title, String subtitle, String image) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
    }

    public Banner(String title, String subtitle, String image) {
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
