package team10.group_app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import team10.group_app.domain.Assessment;
import team10.group_app.domain.Coursework;
import team10.group_app.repository.CourseworkRepository;


import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CourseworkTests extends AssessmentTests{

    
	@Mock
	private CourseworkRepository courseworkRepository;

    //@InjectMocks
    private Coursework assessmentUnderTest = new Coursework();

    @Override
    protected Assessment getAssessment(){
        return new Coursework();
    }

	@Test
	void testSpecificSettersAndGetters() {
        LocalDateTime deadline = LocalDateTime.now();

        //setters
        assessmentUnderTest.setDeadline(deadline);
        assessmentUnderTest.setSpecification("testSpecification");

        //checkers
        assertEquals(deadline, assessmentUnderTest.getDeadline());
        assertEquals("testSpecification", assessmentUnderTest.getSpecification());
	}
  
    @Test
	void testDeadlineIsNotNull() {
		assessmentUnderTest.setId(100);
		assessmentUnderTest.setName("testName");
		assessmentUnderTest.setReleasedToStudents(false);
        assessmentUnderTest.setDeadline(null);
        assessmentUnderTest.setSpecification("testSpecification");

		when(courseworkRepository.save(ArgumentMatchers.any(Coursework.class)))
				.thenThrow(new IllegalArgumentException("releasedToStudents cannot be null"));

		assertThrows(IllegalArgumentException.class, () -> courseworkRepository.save(assessmentUnderTest));
	}

    @Test
	void testSpecificationIsNotNull() {
        LocalDateTime deadline = LocalDateTime.now();

		assessmentUnderTest.setId(100);
		assessmentUnderTest.setName("testName");
		assessmentUnderTest.setReleasedToStudents(false);
        assessmentUnderTest.setDeadline(deadline);
        assessmentUnderTest.setSpecification(null);

		when(courseworkRepository.save(ArgumentMatchers.any(Coursework.class)))
				.thenThrow(new IllegalArgumentException("specification cannot be null"));

		assertThrows(IllegalArgumentException.class, () -> courseworkRepository.save(assessmentUnderTest));
	}

}