package com.mini.project.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
@Entity
@Table(name = "Trainers")
public class Trainer {

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CourseId")
    private Course course;


}
