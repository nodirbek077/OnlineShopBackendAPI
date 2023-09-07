package uz.supersite.entity;

import jakarta.persistence.*;
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
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(name = "from_salary")
    private Double fromSalary;

    @Column(name = "to_salary")
    private Double toSalary;

    @Column(name = "short_description", nullable = false)
    private String shortDescription;

    @Column(length = 128)
    private String image;

    private boolean isActive;

}
