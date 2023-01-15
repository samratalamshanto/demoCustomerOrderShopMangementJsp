package com.example.customerdemoproject.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    public ResponseDataCustomer getAllCustomer() {
        ResponseDataCustomer responseDataCustomer = new ResponseDataCustomer();
        try {
            responseDataCustomer.setMsg("Successfully Get All the Customers.");
            responseDataCustomer.setResponseCode(200);
//            responseDataCustomer.setCustomers(customerRepo.findAll());
        } catch (Exception e) {
            System.out.println(e);
            responseDataCustomer.setResponseCode(500);
            responseDataCustomer.setMsg(String.format("Error: %s", e.getMessage()));
        }
        return responseDataCustomer;
    }

    public ResponseDataCustomer getCustomer(Long id) {
        ResponseDataCustomer responseDataCustomer = new ResponseDataCustomer();
        if (customerRepo.existsById(id)) {
            responseDataCustomer.setMsg("Successfully got the data");
            responseDataCustomer.setResponseCode(200);
            CustomerDao customerDao = customerEntityToCustomerDaoTransform(customerRepo.findById(id).get());
            List<CustomerDao> list = new ArrayList<>();
            list.add(customerDao);
            responseDataCustomer.setCustomers(list);

        } else {
            responseDataCustomer.setMsg("No data found.");
            responseDataCustomer.setResponseCode(404);

        }
        return responseDataCustomer;
    }

    public ResponseDataCustomer addCustomer(CustomerDao customer) {
        ResponseDataCustomer responseDataCustomer = new ResponseDataCustomer();
        CustomerEntity customerEntity = customerDaoToCustomerEntityTransform(customer);

        try {
            customerRepo.save(customerEntity);
            responseDataCustomer.setMsg("Successfully Add the Customer.");
            responseDataCustomer.setResponseCode(201);
//            responseDataCustomer.setCustomers(customerRepo.findAll());
        } catch (Exception e) {
            System.out.println(e);
            responseDataCustomer.setResponseCode(500);
            responseDataCustomer.setMsg(String.format("Error: %s", e.getMessage()));
        }
        return responseDataCustomer;
    }

    public ResponseDataCustomer addManyCustomer(List<CustomerEntity> customerEntityList) {
        ResponseDataCustomer responseDataCustomer = new ResponseDataCustomer();
        try {
            List<CustomerEntity> listOfCustomers = customerRepo.saveAll(customerEntityList);
            if (listOfCustomers.equals(customerEntityList)) {
                responseDataCustomer.setMsg("Successfully Added  All the Customers");
                responseDataCustomer.setResponseCode(201);
//                responseDataCustomer.setCustomers(listOfCustomers);

            } else {
                responseDataCustomer.setMsg("Error! Cannot add all the customers.");
                responseDataCustomer.setResponseCode(500);
            }
        } catch (Exception e) {
            System.out.println(e);
            responseDataCustomer.setResponseCode(500);
            responseDataCustomer.setMsg(String.format("Error: %s", e.getMessage()));
        }
        return responseDataCustomer;
    }

    public ResponseDataCustomer deleteCustomer(Long id) {
        ResponseDataCustomer responseDataCustomer = new ResponseDataCustomer();
        try {
            if (customerRepo.existsById(id)) {
                customerRepo.deleteById(id);
                responseDataCustomer.setMsg("Successfully Delete the Customer.");
                responseDataCustomer.setResponseCode(200);
            } else {
                responseDataCustomer.setMsg("Customer does not exist.");
                responseDataCustomer.setResponseCode(204);
            }
        } catch (Exception e) {
            System.out.println(e);
            responseDataCustomer.setResponseCode(500);
            responseDataCustomer.setMsg(String.format("Error: %s", e.getMessage()));
        }
        return responseDataCustomer;
    }

    public ResponseDataCustomer deleteAllCustomer() {
        ResponseDataCustomer responseDataCustomer = new ResponseDataCustomer();
        try {
            customerRepo.deleteAll();
            responseDataCustomer.setMsg("Successfully Delete All the Customers.");
            responseDataCustomer.setResponseCode(200);
//            responseDataCustomer.setCustomers(customerRepo.findAll());
        } catch (Exception e) {
            System.out.println(e);
            responseDataCustomer.setResponseCode(500);
            responseDataCustomer.setMsg(String.format("Error: %s", e.getMessage()));
        }
        return responseDataCustomer;
    }

    public CustomerEntity customerDaoToCustomerEntityTransform(CustomerDao customer) {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault(); // or: ZoneId.of("Europe/Oslo");
        Long timeCreatedCustomerInLong = localDateTime.atZone(zoneId).toEpochSecond();

        CustomerEntity customerEntity = new CustomerEntity();

        customerEntity.setName(customer.getName());
        customerEntity.setCustomer_class(customer.getCustomer_class());
        customerEntity.setCustomerActive(1);
        customerEntity.setCustomerEmail(customer.getCustomerEmail());
        customerEntity.setCustomerPhoneNum(customer.getCustomerPhoneNum());
        customerEntity.setCustomerCreated(timeCreatedCustomerInLong);

        String birthdayWithTime = String.format("%s 00:00", customer.getCustomerBirthday());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime1 = LocalDateTime.parse(birthdayWithTime, dateTimeFormatter);
        Long birthdayTimeInLong = localDateTime1.atZone(zoneId).toEpochSecond();
        customerEntity.setCustomerBirthday(birthdayTimeInLong);

        return customerEntity;
    }

    public CustomerDao customerEntityToCustomerDaoTransform(CustomerEntity customerEntity) {
        CustomerDao customerDao = new CustomerDao();
        customerDao.setId(customerEntity.getId());
        customerDao.setName(customerEntity.getName());
        customerDao.setCustomer_class(customerEntity.getCustomer_class());
        customerDao.setCustomerEmail(customerEntity.getCustomerEmail());
        customerDao.setCustomerPhoneNum(customerEntity.getCustomerPhoneNum());
        customerDao.setCustomerActive(customerEntity.getCustomerActive());

        LocalDate localDate = Instant.ofEpochSecond(customerEntity.getCustomerBirthday())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        customerDao.setCustomerBirthday(localDate.toString());

        LocalDateTime localDateTime = Instant.ofEpochSecond(customerEntity.getCustomerCreated())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String customerCreatedTimeString = localDateTime.format(customFormat);
        customerDao.setCustomerCreated(customerCreatedTimeString);

        return customerDao;
    }

}
