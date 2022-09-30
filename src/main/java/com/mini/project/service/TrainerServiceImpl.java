package com.mini.project.service;

import com.mini.project.RestSecurityConfiguration;
import com.mini.project.dto.trainer.InsertTrainerDTO;
import com.mini.project.dto.trainer.UpdateTrainerDTO;
import com.mini.project.entity.Account;
import com.mini.project.entity.Course;
import com.mini.project.entity.Trainer;
import com.mini.project.repository.AccountRepository;
import com.mini.project.repository.CourseRepository;
import com.mini.project.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService{

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AccountRepository accountRepository;

    private int rowsInPage = 5;

    @Override
    public Long insertTrainer(InsertTrainerDTO dto) {
        PasswordEncoder encoder = RestSecurityConfiguration.getPasswordEncoder();

        Course course = courseRepository.getBySport(dto.getSport());

        Account account = new Account(
                dto.getUsername(),
                encoder.encode(dto.getPassword()),
                "Trainer"
        );
        Trainer trainer = new Trainer(
                dto.getId(),
                account,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getPhone(),
                dto.getAddress(),
                course
        );

        Trainer respond = trainerRepository.save(trainer);

        return respond.getId();
    }

    @Override
    public List<Trainer> getTrainer(Integer page, String fullName) {
        Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
        List<Trainer> grid = trainerRepository.findAllTrainer(fullName, pagination);
        return grid;
    }

    @Override
    public UpdateTrainerDTO getUpdateTrainer(Long id) {
        Optional<Trainer> optional = trainerRepository.findById(id);
        Trainer entity = optional.get();
        UpdateTrainerDTO dto = new UpdateTrainerDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPhone(),
                entity.getAddress(),
                "",
                ""
        );
        return dto;
    }

    @Override
    public void updateTrainer(UpdateTrainerDTO dto) {
        PasswordEncoder encoder = RestSecurityConfiguration.getPasswordEncoder();
        Optional<Trainer> optional = trainerRepository.findById(dto.getId());
        Trainer updateTrainer = null;
        if (optional.isPresent()){
            updateTrainer = optional.get();
            updateTrainer.setId(dto.getId());
//            updateTrainer.getAccount().setUsername(optional.get().getFirstName());
            updateTrainer.getAccount().setPassword(encoder.encode(dto.getPassword()));
            updateTrainer.setFirstName(dto.getFirstName());
            updateTrainer.setLastName(dto.getLastName());
            updateTrainer.setPhone(dto.getPhone());
            updateTrainer.setAddress(dto.getAddress());
//            updateTrainer.setCourse(optional.get().getCourse());
        }

        trainerRepository.save(updateTrainer);
    }

    @Override
    public Trainer getById(Long id) {
        Optional<Trainer> optional = trainerRepository.findById(id);
        Trainer trainer = null;
        if (optional.isPresent()) {
            trainer = optional.get();
        }
        return trainer;
    }

    @Override
    public void deleteById(Long id) {
//        Optional<Trainer> optional = trainerRepository.findById(id);
//        Trainer trainer = null;
//        if (optional.isPresent()){
//            trainer = optional.get();
//            accountRepository.deleteById(trainer.getAccount().getUsername());
//        }
        trainerRepository.deleteById(id);
    }
}
