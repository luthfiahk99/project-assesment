package com.mini.project.service;

import com.mini.project.dto.course.CourseGridDTO;
import com.mini.project.dto.course.UpsertCourseDTO;
import com.mini.project.entity.Course;
import com.mini.project.entity.Customer;

import java.util.List;

public interface CourseService {

    Long insertCourse(UpsertCourseDTO dto);

    List<Course> getCourseGrid(Integer page, String sport);

    List<CourseGridDTO> getCourses(Integer page, String sport);

    UpsertCourseDTO getUpdateCourse(String sport);

    void updateCourse(UpsertCourseDTO dto);

    Long hasTrainer(Long id);

    Boolean deleteById(Long id);

    Course findById(Long id);

    List<Customer> getCustomerGridByCourse(Long id);
}
