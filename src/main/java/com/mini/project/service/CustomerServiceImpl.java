package com.mini.project.service;

import com.mini.project.RestSecurityConfiguration;
import com.mini.project.dto.customer.AssignCourseDTO;
import com.mini.project.dto.customer.InsertCustomerDTO;
import com.mini.project.dto.customer.UpdateCustomerDTO;
import com.mini.project.dto.trainer.UpdateTrainerDTO;
import com.mini.project.entity.Account;
import com.mini.project.entity.Course;
import com.mini.project.entity.Customer;
import com.mini.project.entity.Trainer;
import com.mini.project.repository.AccountRepository;
import com.mini.project.repository.CourseRepository;
import com.mini.project.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AccountRepository accountRepository;

    private int rowsInPage = 5;

    @Override
    public Long insertCustomer(InsertCustomerDTO dto) {
        PasswordEncoder encoder = RestSecurityConfiguration.getPasswordEncoder();

        Account account = new Account(
                dto.getUsername(),
                encoder.encode(dto.getPassword()),
                "Customer"
        );
        Customer customer = new Customer(
                dto.getId(),
                account,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getPhone(),
                dto.getAddress()
        );

        Customer respond = customerRepository.save(customer);

        return respond.getId();
    }

    @Override
    public List<Customer> getCustomers(Integer page, String fullName) {
        Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
        List<Customer> grid = customerRepository.findAllCustomer(fullName, pagination);
        return grid;
    }

    @Override
    public UpdateCustomerDTO getUpdateCustomer(Long id) {
        Optional<Customer> optional = customerRepository.findById(id);
        Customer entity = optional.get();
        UpdateCustomerDTO dto = new UpdateCustomerDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPhone(),
                entity.getAddress(),
                "",
                ""
        );
        return dto;
    }

    @Override
    public void updateCustomer(UpdateCustomerDTO dto) {
        PasswordEncoder encoder = RestSecurityConfiguration.getPasswordEncoder();
        Optional<Customer> optional = customerRepository.findById(dto.getId());
        Customer updateCustomer = null;
        if (optional.isPresent()){
            updateCustomer = optional.get();
            updateCustomer.setId(dto.getId());
            updateCustomer.getAccount().setPassword(encoder.encode(dto.getPassword()));
            updateCustomer.setFirstName(dto.getFirstName());
            updateCustomer.setLastName(dto.getLastName());
            updateCustomer.setPhone(dto.getPhone());
            updateCustomer.setAddress(dto.getAddress());
        }

        customerRepository.save(updateCustomer);
    }

    @Override
    public Customer getById(Long id) {
        Optional<Customer> optional = customerRepository.findById(id);
        Customer customer = null;
        if (optional.isPresent()) {
            customer = optional.get();
        }
        return customer;
    }

    @Override
    public void deleteById(Long id) {
        Optional<Customer> optional = customerRepository.findById(id);
        Customer customer = null;
        if (optional.isPresent()){
            customer = optional.get();
            accountRepository.deleteById(customer.getAccount().getUsername());
        }
        customerRepository.deleteById(id);
    }

    @Override
    public void assignCourse(AssignCourseDTO dto) {
        Optional<Course> nullableCourse = courseRepository.findById(dto.getCourseId());
        Course course = nullableCourse.get();

        Optional<Customer> nullableCustomer = customerRepository.findById(dto.getCustomerId());
        Customer customer = nullableCustomer.get();

        customer.getCourses().add(course);
        customerRepository.save(customer);
    }

    @Override
    public List<Course> getCourseGridByCustomer(Long customerId) {
        List<Course> courses = courseRepository.findCourses(customerId);

        return courses;
    }
}
