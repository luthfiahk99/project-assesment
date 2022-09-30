package com.mini.project.repository;

import com.mini.project.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = """
            SELECT *
            FROM Customers AS cu
            WHERE CONCAT(cu.FirstName, ' ', cu.LastName) LIKE %:fullName%
            """, nativeQuery = true)
    List<Customer> findAllCustomer(@Param("fullName") String fullName, Pageable pagination);

    @Query("""
            SELECT new com.mini.project.entity.Customer(cu.id, CONCAT(cu.firstName, ' ', cu.lastName))
            FROM Course AS co
                JOIN co.customers AS cu
            WHERE co.id = :courseId
            """)
    List<Customer> findCustomers(@Param("courseId") Long courseId);
}
