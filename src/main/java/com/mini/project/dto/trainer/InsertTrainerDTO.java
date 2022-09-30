package com.mini.project.dto.trainer;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
public class InsertTrainerDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String sport;
    private String password;
    private String passwordConfirmation;
}
