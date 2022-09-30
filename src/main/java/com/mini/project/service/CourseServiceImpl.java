package com.mini.project.service;

import com.mini.project.dto.course.CourseGridDTO;
import com.mini.project.dto.course.UpsertCourseDTO;
import com.mini.project.entity.Course;
import com.mini.project.entity.Customer;
import com.mini.project.repository.CourseRepository;
import com.mini.project.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private int rowsInPage = 10;

    @Override
    public Long insertCourse(UpsertCourseDTO dto) {

        Course newCourse = new Course(
                dto.getId(),
                dto.getSport(),
                dto.getPrice(),
                dto.getTotalWeek(),
                dto.getDescription()
        );

        Course respond = courseRepository.save(newCourse);

        return respond.getId();
    }

    @Override
    public List<Course> getCourseGrid(Integer page, String sport) {
        Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
        List<Course> grid = courseRepository.findAll(sport, pagination);
        return grid;
    }

    @Override
    public UpsertCourseDTO getUpdateCourse(String sport) {
        return courseRepository.getBySportDto(sport);
    }

    @Override
    public void updateCourse(UpsertCourseDTO dto) {

        Optional<Course> optional = courseRepository.findById(dto.getId());
        Course updateCourse = null;
        if (optional.isPresent()){
            updateCourse = optional.get();
            updateCourse.setId(dto.getId());
            updateCourse.setPrice(dto.getPrice());
            updateCourse.setTotalWeek(dto.getTotalWeek());
            updateCourse.setDescription(dto.getDescription());
        }

        courseRepository.save(updateCourse);
    }

    @Override
    public Long hasTrainer(Long id) {
        Long hasTrainer = courseRepository.countById(id);

        return hasTrainer;
    }

    @Override
    public Boolean deleteById(Long id) {

        long trainer = hasTrainer(id);
        if(trainer == 0) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Course findById(Long id) {
        Optional<Course> optional = courseRepository.findById(id);
        Course course = null;
        if (optional.isPresent()){
            course = optional.get();
        }
        return course;
    }

    @Override
    public List<Customer> getCustomerGridByCourse(Long courseId) {
        List<Customer> customers = customerRepository.findCustomers(courseId);

        return customers;
    }

}
