package com.example.customerdemoproject.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping
    public Object getAllCustomer() {
        return customerService.getAllCustomer();
    }

    @GetMapping("/{id}")
    public Object getCustomer(@PathVariable Long id) {
        return customerService.getCustomer(id);
    }

    @PostMapping("/addCustomer")
    public Object addCustomer(@RequestBody CustomerDao customer)
    {

        return customerService.addCustomer(customer);
    }

    @PostMapping("/addManyCustomers")
    public Object addManyCustomers(@RequestBody List<CustomerEntity> customerEntityList)
    {
        return customerService.addManyCustomer(customerEntityList);
    }

}
