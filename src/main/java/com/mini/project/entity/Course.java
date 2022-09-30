package com.mini.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
@Entity
@Table(name = "Courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Sport")
    private String sport;
    @Column(name = "Price")
    private BigDecimal price;
    @Column(name = "TotalWeek")
    private Integer totalWeek;
    @Column(name = "Description")
    private String description;

//    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
//    private Trainer trainer;

//    @OneToMany(mappedBy = "course")
//    Set<CustomerCourse> customerCourses;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="CustomerCourse",
            joinColumns=@JoinColumn(name="CourseId"),
            inverseJoinColumns=@JoinColumn(name="CustomerId"))
    private List<Customer> customers;

    public Course(Long id, String sport, BigDecimal price, Integer totalWeek, String description) {
        this.id = id;
        this.sport = sport;
        this.price = price;
        this.totalWeek = totalWeek;
        this.description = description;
    }

    public Course(Long id, String sport) {
        this.id = id;
        this.sport = sport;
    }
}
