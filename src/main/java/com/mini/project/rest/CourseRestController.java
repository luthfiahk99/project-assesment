package com.mini.project.rest;

import com.mini.project.dto.course.CourseGridDTO;
import com.mini.project.dto.course.UpsertCourseDTO;
import com.mini.project.entity.Course;
import com.mini.project.entity.Customer;
import com.mini.project.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseRestController {

    @Autowired
    private CourseService courseService;

    @PostMapping("")
    public ResponseEntity<Object> post(@RequestBody UpsertCourseDTO dto,
                                       BindingResult bindingResult){

        try{
            if(!bindingResult.hasErrors()){
                dto.setId(0l);
                Long respondId = courseService.insertCourse(dto);
                dto.setId(respondId);
                return ResponseEntity.status(HttpStatus.CREATED).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value ={
            "",
            "/{page}"})
    public ResponseEntity<Object> get(@PathVariable(required = false) Integer page,
                                      @RequestParam(defaultValue = "") String sport){
        page = (page == null) ? 1 : page;
        try{
            List<Course> courses = courseService.getCourseGrid(page, sport);
            return ResponseEntity.status(HttpStatus.OK).body(courses);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/get/{sport}")
    public ResponseEntity<Object> getBySport(@PathVariable(required = true) String sport) {
        try {
            UpsertCourseDTO dto = courseService.getUpdateCourse(sport);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertCourseDTO dto,
                                      BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                courseService.updateCourse(dto);
                return ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Long id){
        try{
            Course course = courseService.findById(id);
            if(courseService.hasTrainer(id) == 0){
            courseService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(course.getSport());
            }else {return ResponseEntity.status(HttpStatus.OK).body("This Course has Trainer");}
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> getDetail(@PathVariable(required = true) Long id){

        List<Customer> courseCustomer = courseService.getCustomerGridByCourse(id);

        return new ResponseEntity<>(courseCustomer, HttpStatus.OK);
    }
}
