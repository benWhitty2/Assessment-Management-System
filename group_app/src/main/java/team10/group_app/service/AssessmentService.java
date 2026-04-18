package team10.group_app.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import team10.group_app.domain.Assessment;
import team10.group_app.repository.AssessmentRepository;

import java.util.List;

@Service
@Transactional
public class AssessmentService {

    private final AssessmentRepository repository;
    private static final String ASSESSMENT_NOT_FOUND = "Assessment does not exist";

    public AssessmentService(AssessmentRepository repository) {
        this.repository = repository;
    }

    public Assessment createAssessment(Assessment newAssessment) {
        return repository.save(newAssessment);
    }

    public List<Assessment> getAllAssessments() {
        return repository.findAll();
    }

    public Assessment getAssessmentById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ASSESSMENT_NOT_FOUND));
    }

    public Assessment updateAssessment(Assessment updatedAssessment, int id) {
        Assessment stored = getAssessmentById(id);
        stored.setName(updatedAssessment.getName());
        stored.setSetter(updatedAssessment.getSetter());
        stored.setChecker(updatedAssessment.getChecker());
        stored.setReleasedToStudents(updatedAssessment.getReleasedToStudents());
        return repository.save(stored);
    }

    public void deleteAssessment(int id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ASSESSMENT_NOT_FOUND);
        }
        repository.deleteById(id);
    }
}
