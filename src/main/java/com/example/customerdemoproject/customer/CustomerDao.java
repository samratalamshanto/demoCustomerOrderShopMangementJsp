package com.example.customerdemoproject.customer;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDao {
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private CustomerEnum customer_class;
    private Integer customerActive;
    private String customerEmail;

    @Column(unique = true)
    @Pattern(regexp = "^(880)?1\\d{9}$", message = "Phone number must be of 13 digits and starts with `880`")
    private String customerPhoneNum;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String customerBirthday;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private String customerCreated;
}
