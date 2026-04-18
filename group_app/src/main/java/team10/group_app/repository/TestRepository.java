package team10.group_app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import team10.group_app.domain.Test;
import team10.group_app.domain.User;
import java.time.LocalDateTime;


public interface TestRepository extends JpaRepository<Test, Integer> {
    
    List<Test> findBySetter(User setter);

    List<Test> findByChecker(User checker);

    List<Test> findByName(String name);

    List<Test> findByReleasedToStudents(Boolean releasedToStudents);

    List<Test> findByDuration(LocalDateTime duration);
    
    List<Test> findByStartTime(LocalDateTime startTime);

    List<Test> findByOngoing(Boolean ongoing);

    List<Test> findByAutograded(Boolean autograded);
}
