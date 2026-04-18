package team10.group_app.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import team10.group_app.domain.Test;
import team10.group_app.repository.TestRepository;

import java.util.List;

@Service
@Transactional
public class TestService {

    private final TestRepository repository;
    private static final String TEST_NOT_FOUND = "Test does not exist";

    public TestService(TestRepository repository) {
        this.repository = repository;
    }

    public Test createTest(Test newTest) {
        return repository.save(newTest);
    }

    public List<Test> getAllTests() {
        return repository.findAll();
    }

    public Test getTestById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, TEST_NOT_FOUND));
    }

    public Test updateTest(Test updatedTest, int id) {
        Test stored = getTestById(id);
        stored.setName(updatedTest.getName());
        stored.setSetter(updatedTest.getSetter());
        stored.setChecker(updatedTest.getChecker());
        stored.setStartTime(updatedTest.getStartTime());
        stored.setDuration(updatedTest.getDuration());
        stored.setOngoing(updatedTest.getOngoing());
        stored.setAutograded(updatedTest.getAutograded());
        stored.setReleasedToStudents(updatedTest.getReleasedToStudents());
        return repository.save(stored);
    }

    public void deleteTest(int id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, TEST_NOT_FOUND);
        }
        repository.deleteById(id);
    }
}
