package com.mini.project.service;

import com.mini.project.dto.trainer.InsertTrainerDTO;
import com.mini.project.dto.trainer.UpdateTrainerDTO;
import com.mini.project.entity.Trainer;

import java.util.List;

public interface TrainerService {
    Long insertTrainer(InsertTrainerDTO dto);

    List<Trainer> getTrainer(Integer page, String fullName);

    UpdateTrainerDTO getUpdateTrainer(Long id);

    void updateTrainer(UpdateTrainerDTO dto);

    Trainer getById(Long id);

    void deleteById(Long id);
}
