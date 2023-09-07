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
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String location;

    @Column(name = "customer_name",length = 30, nullable = false)
    private String customerName;

    @Column(name = "phone_number",length = 15)
    private String phoneNumber;

    private String message;
}
