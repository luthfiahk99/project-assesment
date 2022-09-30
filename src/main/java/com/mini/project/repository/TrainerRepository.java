package com.mini.project.repository;

import com.mini.project.dto.trainer.TrainerGridDTO;
import com.mini.project.entity.Trainer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    @Query(value = """
            SELECT *
            FROM Trainers AS tr
            WHERE CONCAT(tr.FirstName, ' ', tr.LastName) LIKE %:fullName%
            """, nativeQuery = true)
    List<Trainer> findAllTrainer(@Param("fullName") String fullName, Pageable pagination);
}
