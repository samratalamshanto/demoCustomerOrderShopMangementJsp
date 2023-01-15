package com.example.customerdemoproject.customer;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private CustomerEnum customer_class;

    private Integer customerActive;


    private String customerEmail;

    @Column(unique = true)
    @Pattern(regexp = "^(880)?1\\d{9}$", message = "Phone number must be of 13 digits and starts with `880`")
    private String customerPhoneNum;

    private Long customerBirthday;
    private Long customerCreated;

}
