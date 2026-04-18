package team10.group_app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import team10.group_app.domain.Assessment;
import team10.group_app.repository.TestRepository;
import team10.group_app.service.UserService;
import team10.group_app.repository.TestRepository;

import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TestTests extends AssessmentTests{

	@Mock
	private TestRepository testRepository;

	//@InjectMocks
	
	private team10.group_app.domain.Test assessmentUnderTest = new team10.group_app.domain.Test();

	@Override
    protected Assessment getAssessment(){
        return new team10.group_app.domain.Test();
    }




	@Test
	void testSpecificSettersAndGetters() {
        LocalDateTime duration = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.now();

        assessmentUnderTest.setDuration(duration);
        assessmentUnderTest.setStartTime(startTime);
        assessmentUnderTest.setOngoing(true);
		assessmentUnderTest.setAutograded(true);

        assertEquals(duration, assessmentUnderTest.getDuration());
        assertEquals(startTime, assessmentUnderTest.getStartTime());
        assertEquals(true, assessmentUnderTest.getOngoing());
		assertEquals(true, assessmentUnderTest.getAutograded());

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
		assessmentUnderTest.setAutograded(false);

		when(testRepository.save(ArgumentMatchers.any(team10.group_app.domain.Test.class)))
				.thenThrow(new IllegalArgumentException("duration cannot be null"));

		assertThrows(IllegalArgumentException.class, () -> testRepository.save(assessmentUnderTest));
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
		assessmentUnderTest.setAutograded(false);

		when(testRepository.save(ArgumentMatchers.any(team10.group_app.domain.Test.class)))
				.thenThrow(new IllegalArgumentException("startTime cannot be null"));

		assertThrows(IllegalArgumentException.class, () -> testRepository.save(assessmentUnderTest));
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
		assessmentUnderTest.setAutograded(false);

		when(testRepository.save(ArgumentMatchers.any(team10.group_app.domain.Test.class)))
				.thenThrow(new IllegalArgumentException("ongoing cannot be null"));

		assertThrows(IllegalArgumentException.class, () -> testRepository.save(assessmentUnderTest));
	}

	 @Test
	void testAutogradedIsNotNull() {
        LocalDateTime duration = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.now();

		assessmentUnderTest.setId(100);
		assessmentUnderTest.setName("testName");
		assessmentUnderTest.setReleasedToStudents(false);
        assessmentUnderTest.setDuration(duration);
        assessmentUnderTest.setStartTime(startTime);
        assessmentUnderTest.setOngoing(false);
		assessmentUnderTest.setAutograded(null);

		when(testRepository.save(ArgumentMatchers.any(team10.group_app.domain.Test.class)))
				.thenThrow(new IllegalArgumentException("autograded cannot be null"));

		assertThrows(IllegalArgumentException.class, () -> testRepository.save(assessmentUnderTest));
	}

}