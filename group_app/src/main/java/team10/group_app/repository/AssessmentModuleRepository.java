package team10.group_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team10.group_app.domain.Assessment;
import team10.group_app.domain.AssessmentModule;
import java.util.List;
import java.util.Optional;


public interface AssessmentModuleRepository extends JpaRepository<AssessmentModule, Integer> {
    
    List<AssessmentModule> findByAssessment(Assessment assessment);  

    List<AssessmentModule> findByModule(Module module);

    Optional<AssessmentModule> findByAssessmentAndModule(Assessment assessment, Module module);
}
