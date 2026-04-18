package team10.group_app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import team10.group_app.repository.ExamRepository;

import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import team10.group_app.domain.Assessment;
import team10.group_app.domain.Exam;
import team10.group_app.domain.User;

import java.time.LocalDateTime;



@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ExamTests extends AssessmentTests{

    @Mock
	private ExamRepository examRepository;

    //@InjectMocks
    private Exam assessmentUnderTest = new Exam();

    @Override
    protected Assessment getAssessment(){
        return new Exam();
    }

	@Test
	void testSpecificSettersAndGetters() {
        LocalDateTime duration = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.now();
        User externalExaminer = new User("testExternalExaminer","testPassword","testRole");

        assessmentUnderTest.setDuration(duration);
        assessmentUnderTest.setStartTime(startTime);
        assessmentUnderTest.setOngoing(true);
        assessmentUnderTest.setFeedback("testFeedback");
        assessmentUnderTest.setResponse("testResponse");
        assessmentUnderTest.setExternalExaminer(externalExaminer);
        

        assertEquals(duration, assessmentUnderTest.getDuration());
        assertEquals(startTime, assessmentUnderTest.getStartTime());
        assertEquals(true, assessmentUnderTest.getOngoing());
        assertEquals("testFeedback", assessmentUnderTest.getFeedback());
        assertEquals("testResponse", assessmentUnderTest.getResponse());
        assertEquals(externalExaminer, assessmentUnderTest.getExternalExaminer());
        
        


	}

    @Test
	void testDurationIsNotNull() {
        LocalDateTime startTime = LocalDateTime.now();

		assessmentUnderTest.setId(100);
		assessmentUnderTest.setName("testName");
		assessmentUnderTest.setReleasedToStudents(false);
        assessmentUnderTest.setDuration(null);
        assessmentUnderTest.setStartTime(startTime);
        assessmentUnderTest.setOngoing(false);

		when(examRepository.save(ArgumentMatchers.any(Exam.class)))
				.thenThrow(new IllegalArgumentException("duration cannot be null"));

		assertThrows(IllegalArgumentException.class, () -> examRepository.save(assessmentUnderTest));
	}

@Test
	void testStartTimeIsNotNull() {
        LocalDateTime duration = LocalDateTime.now();

		assessmentUnderTest.setId(100);
		assessmentUnderTest.setName("testName");
		assessmentUnderTest.setReleasedToStudents(false);
        assessmentUnderTest.setDuration(duration);
        assessmentUnderTest.setStartTime(null);
        assessmentUnderTest.setOngoing(false);

		when(examRepository.save(ArgumentMatchers.any(Exam.class)))
				.thenThrow(new IllegalArgumentException("startTime cannot be null"));

		assertThrows(IllegalArgumentException.class, () -> examRepository.save(assessmentUnderTest));
	}

    @Test
	void testOngoingIsNotNull() {
        LocalDateTime duration = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.now();

		assessmentUnderTest.setId(100);
		assessmentUnderTest.setName("testName");
		assessmentUnderTest.setReleasedToStudents(false);
        assessmentUnderTest.setDuration(duration);
        assessmentUnderTest.setStartTime(startTime);
        assessmentUnderTest.setOngoing(null);

		when(examRepository.save(ArgumentMatchers.any(Exam.class)))
				.thenThrow(new IllegalArgumentException("ongoing cannot be null"));

		assertThrows(IllegalArgumentException.class, () -> examRepository.save(assessmentUnderTest));
	}
}
    