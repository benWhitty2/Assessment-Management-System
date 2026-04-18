package team10.group_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team10.group_app.domain.Assessment;
import team10.group_app.domain.User;
import java.util.List;

public interface AssessmentRepository extends JpaRepository <Assessment, Integer>{

     List<Assessment> findBySetter(User setter);

    List<Assessment> findByChecker(User checker);

    List<Assessment> findByName(String name);

    List<Assessment> findByReleasedToStudents(Boolean releasedToStudents);

}
