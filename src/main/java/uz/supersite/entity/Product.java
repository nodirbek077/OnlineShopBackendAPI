package uz.supersite.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long barCode;

    @Column(unique = true, length = 255, nullable = false)
    private String name;

    @Column(unique = true, length = 255, nullable = false)
    private String alias;

    @Column(name = "short_description", length = 512, nullable = false)
    private String shortDescription;

    @Column(name = "full_description", length = 4096, nullable = false)
    private String fullDescription;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false, updatable = false)
    private Date createdTime;

    @CreationTimestamp
    @Column(name = "updated_time")
    private Date updatedTime;

    private boolean enabled;

    @Column(name = "in_stock")
    private boolean inStock;

    @Column(name = "discount_percent")
    private float discountPercent;

    @ManyToAny
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToAny
    @JoinColumn(name = "brand_id")
    private Brand brand;

}
