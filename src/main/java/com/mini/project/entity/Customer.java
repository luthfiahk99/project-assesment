package com.mini.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
@Entity
@Table(name = "Customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Username")
    private Account account;

    @Column(name = "FirstName")
    private String firstName;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "Address")
    private String address;

//    @ManyToMany(mappedBy = "customer")
//    Set<CustomerCourse> customerCourses;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="CustomerCourse",
            joinColumns=@JoinColumn(name="CustomerId"),
            inverseJoinColumns=@JoinColumn(name="CourseId"))
    private List<Course> courses;

    public Customer(Long id, Account account, String firstName, String lastName, String phone, String address) {
        this.id = id;
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    public Customer(Long id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }
}
