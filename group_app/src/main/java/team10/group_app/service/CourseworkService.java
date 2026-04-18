package team10.group_app.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import team10.group_app.domain.Coursework;
import team10.group_app.repository.CourseworkRepository;

import java.util.List;

@Service
@Transactional
public class CourseworkService {

    private final CourseworkRepository repository;
    private static final String COURSEWORK_NOT_FOUND = "Coursework does not exist";

    public CourseworkService(CourseworkRepository repository) {
        this.repository = repository;
    }

    public Coursework createCoursework(Coursework newCoursework) {
        return repository.save(newCoursework);
    }

    public List<Coursework> getAllCourseworks() {
        return repository.findAll();
    }

    public Coursework getCourseworkById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, COURSEWORK_NOT_FOUND));
    }

    public Coursework updateCoursework(Coursework updatedCoursework, int id) {
        Coursework stored = getCourseworkById(id);
        stored.setName(updatedCoursework.getName());
        stored.setSetter(updatedCoursework.getSetter());
        stored.setChecker(updatedCoursework.getChecker());
        stored.setSpecification(updatedCoursework.getSpecification());
        stored.setDeadline(updatedCoursework.getDeadline());
        stored.setReleasedToStudents(updatedCoursework.getReleasedToStudents());
        return repository.save(stored);
    }

    public void deleteCoursework(int id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, COURSEWORK_NOT_FOUND);
        }
        repository.deleteById(id);
    }
}
