package team10.group_app.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import team10.group_app.domain.Exam;
import team10.group_app.repository.ExamRepository;

import java.util.List;

@Service
@Transactional
public class ExamService {

    private final ExamRepository repository;
    private static final String EXAM_NOT_FOUND = "Exam does not exist";

    public ExamService(ExamRepository repository) {
        this.repository = repository;
    }

    public Exam createExam(Exam newExam) {
        return repository.save(newExam);
    }

    public List<Exam> getAllExams() {
        return repository.findAll();
    }

    public Exam getExamById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, EXAM_NOT_FOUND));
    }

    public Exam updateExam(Exam updatedExam, int id) {
        Exam stored = getExamById(id);
        stored.setName(updatedExam.getName());
        stored.setSetter(updatedExam.getSetter());
        stored.setChecker(updatedExam.getChecker());
        stored.setStartTime(updatedExam.getStartTime());
        stored.setDuration(updatedExam.getDuration());
        stored.setOngoing(updatedExam.getOngoing());
        stored.setReleasedToStudents(updatedExam.getReleasedToStudents());
        stored.setFeedback(updatedExam.getFeedback());
        stored.setResponse(updatedExam.getResponse());
        return repository.save(stored);
    }

    public void deleteExam(int id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, EXAM_NOT_FOUND);
        }
        repository.deleteById(id);
    }
}
