package com.mini.project.rest;

import com.mini.project.dto.trainer.InsertTrainerDTO;
import com.mini.project.dto.trainer.UpdateTrainerDTO;
import com.mini.project.entity.Trainer;
import com.mini.project.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/trainer")
public class TrainerRestController {

    @Autowired
    private TrainerService trainerService;

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody InsertTrainerDTO dto,
                                       BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                dto.setId(0l);
                Long respondId = trainerService.insertTrainer(dto);
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
            List<Trainer> trainer = trainerService.getTrainer(page, fullName);
            return ResponseEntity.status(HttpStatus.OK).body(trainer);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getById(@PathVariable(required = true) Long id) {
        try {
            UpdateTrainerDTO dto = trainerService.getUpdateTrainer(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpdateTrainerDTO dto,
                                      BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                trainerService.updateTrainer(dto);
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
//            Trainer trainer = trainerService.getById(id);
            trainerService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

}
