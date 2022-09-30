package com.mini.project.dto.trainer;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class UpdateTrainerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String password;
    private String passwordConfirmation;
}
