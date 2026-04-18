package team10.group_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team10.group_app.domain.Coursework;
import team10.group_app.domain.User;
import java.util.List;



public interface CourseworkRepository extends JpaRepository<Coursework, Integer> {
    
    List<Coursework> findBySetter(User setter);

    List<Coursework> findByChecker(User checker);

    List<Coursework> findByName(String name);

    List<Coursework> findByReleasedToStudents(Boolean releasedToStudents);
}
