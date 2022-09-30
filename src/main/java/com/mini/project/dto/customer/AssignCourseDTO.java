package com.mini.project.dto.customer;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class AssignCourseDTO {

    private Long customerId;
    private Long courseId;
}
