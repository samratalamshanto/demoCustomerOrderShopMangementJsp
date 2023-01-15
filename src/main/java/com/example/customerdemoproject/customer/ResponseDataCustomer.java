package com.example.customerdemoproject.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDataCustomer {
    private String msg;
    private Integer responseCode;
    private List<CustomerDao> customers;
}
