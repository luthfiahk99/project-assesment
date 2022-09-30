package com.mini.project.rest;

import com.mini.project.dto.customer.AssignCourseDTO;
import com.mini.project.dto.customer.InsertCustomerDTO;
import com.mini.project.dto.customer.UpdateCustomerDTO;
import com.mini.project.dto.trainer.InsertTrainerDTO;
import com.mini.project.dto.trainer.UpdateTrainerDTO;
import com.mini.project.entity.Course;
import com.mini.project.entity.Customer;
import com.mini.project.entity.Trainer;
import com.mini.project.repository.CourseRepository;
import com.mini.project.service.CourseService;
import com.mini.project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody InsertCustomerDTO dto,
                                       BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                dto.setId(0l);
                Long respondId = customerService.insertCustomer(dto);
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
                                      @RequestParam(defaultValue = "") String fullName){
        page = (page == null) ? 1 : page;
        try{
            List<Customer> customers = customerService.getCustomers(page, fullName);
            return ResponseEntity.status(HttpStatus.OK).body(customers);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getById(@PathVariable(required = true) Long id) {
        try {
            UpdateCustomerDTO dto = customerService.getUpdateCustomer(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpdateCustomerDTO dto,
                                      BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                customerService.updateCustomer(dto);
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
//            Customer customer = customerService.getById(id);
            customerService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping(value = "/detail")
    public ResponseEntity<Object> postDetail(@RequestBody AssignCourseDTO dto,
                                             BindingResult bindingResult){

        if(!bindingResult.hasErrors()){
            customerService.assignCourse(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> getDetail(@PathVariable(required = true) Long id){

        List<Course> customerCourse = customerService.getCourseGridByCustomer(id);

        return new ResponseEntity<>(customerCourse, HttpStatus.OK);
    }
}
