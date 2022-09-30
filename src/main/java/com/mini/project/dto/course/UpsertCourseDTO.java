package com.mini.project.dto.course;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
public class UpsertCourseDTO {

    private Long id;
    private String sport;
    private BigDecimal price;
    private Integer totalWeek;
    private String description;
}
