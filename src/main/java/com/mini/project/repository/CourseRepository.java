package com.mini.project.repository;

import com.mini.project.dto.course.CourseGridDTO;
import com.mini.project.dto.course.UpsertCourseDTO;
import com.mini.project.entity.Course;
import org.hibernate.query.NativeQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(value = """
            SELECT co.Id, co.Sport, co.Price, co.TotalWeek, co.Description
            FROM Courses AS co
                LEFT JOIN Trainers AS tr ON tr.CourseId = co.Id
            WHERE co.Sport LIKE %:sport%
            """, nativeQuery = true)
    List<Course> findAll(@Param("sport") String sport, Pageable pagination);

    @Query("""
            SELECT co
            FROM Course AS co
            WHERE co.sport = :sport
            """)
    Course getBySport(@Param("sport") String sport);

    @Query("""
            SELECT new com.mini.project.dto.course.UpsertCourseDTO(co.id, co.sport, co.price, co.totalWeek, co.description)
            FROM Course AS co
            WHERE co.sport = :sport
            """)
    UpsertCourseDTO getBySportDto(@Param("sport") String sport);

    @Query("""
			SELECT COUNT(tr.id) 
			FROM Trainer AS tr
			    JOIN tr.course AS co
			WHERE co.id = :id
			""")
    Long countById(@Param("id") Long id);

    @Query("""
            SELECT new com.mini.project.entity.Course(co.id, co.sport)
            FROM Course AS co
                JOIN co.customers AS cu
            WHERE cu.id = :customerId
            """)
    List<Course> findCourses(@Param("customerId") Long customerId);
}
