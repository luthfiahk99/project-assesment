package com.mini.project.service;

import com.mini.project.dto.customer.AssignCourseDTO;
import com.mini.project.dto.customer.InsertCustomerDTO;
import com.mini.project.dto.customer.UpdateCustomerDTO;
import com.mini.project.entity.Course;
import com.mini.project.entity.Customer;

import java.util.List;

public interface CustomerService {
    Long insertCustomer(InsertCustomerDTO dto);

    List<Customer> getCustomers(Integer page, String fullName);

    UpdateCustomerDTO getUpdateCustomer(Long id);

    void updateCustomer(UpdateCustomerDTO dto);

    Customer getById(Long id);

    void deleteById(Long id);

    void assignCourse(AssignCourseDTO dto);

    List<Course> getCourseGridByCustomer(Long id);
}
