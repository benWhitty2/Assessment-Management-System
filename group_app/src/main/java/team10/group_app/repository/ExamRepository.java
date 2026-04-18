package team10.group_app.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import team10.group_app.domain.Exam;
import team10.group_app.domain.User;

public interface ExamRepository extends JpaRepository<Exam, Integer> {
    
    List<Exam> findBySetter(User setter);

    List<Exam> findByChecker(User checker);

    List<Exam> findByName(String name);

    List<Exam> findByReleasedToStudents(Boolean releasedToStudents);

    List<Exam> findByDuration(LocalDateTime duration);
    
    List<Exam> findByStartTime(LocalDateTime startTime);

    List<Exam> findByOngoing(Boolean ongoing);
}
