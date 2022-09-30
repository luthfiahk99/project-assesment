package com.mini.project.dto.customer;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class InsertCustomerDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String password;
    private String passwordConfirmation;
}
