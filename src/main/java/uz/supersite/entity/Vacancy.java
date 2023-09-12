package uz.supersite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vacancies")
public final class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    @NotBlank(message = "Title can't be left blank")
    private String title;

    @Column(name = "from_salary")
    private Double fromSalary;

    @Column(name = "to_salary")
    private Double toSalary;

    @Column(name = "short_description", nullable = false, unique = true)
    @NotBlank(message = "Description can't be left blank")
    private String shortDescription;

    @Column(length = 128)
    private String image;

    private boolean isActive;

    @JsonIgnore
    @Transient
    public String getImagePath() {
        if(this.id == null) return "/images/image-thumbnail.png";
        return "/vacancy-photos/" + this.id + "/" + this.image;
    }
}
