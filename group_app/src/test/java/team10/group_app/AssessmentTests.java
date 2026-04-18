package team10.group_app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import team10.group_app.domain.Assessment;
import team10.group_app.domain.AssessmentModule;
import team10.group_app.domain.Coursework;
import team10.group_app.domain.User;
import team10.group_app.repository.AssessmentRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
abstract class AssessmentTests {

	private Assessment assessmentUnderTest;
	protected abstract Assessment getAssessment();

	@Mock
	private AssessmentRepository assessmentRepository;

	@BeforeEach
	public void setUp(){
		assessmentUnderTest = getAssessment();
	}

	@Test
	void testGeneralSettersAndGetters() {
		User checker = new User("testChecker","testPassword","testRole");
		User setter = new User("testChecker","testPassword","testRole");

		//setters
		assessmentUnderTest.setId(5);
		assessmentUnderTest.setName("TestAssessmentName");
		assessmentUnderTest.setReleasedToStudents(true);
		assessmentUnderTest.setChecker(checker);
		assessmentUnderTest.setSetter(setter);
		
		//checkers
		assertEquals(5, assessmentUnderTest.getId());
		assertEquals("TestAssessmentName", assessmentUnderTest.getName());
		assertEquals(true, assessmentUnderTest.getReleasedToStudents());
		assertEquals(checker, assessmentUnderTest.getChecker());
		assertEquals(setter, assessmentUnderTest.getSetter());
		
	}

	@Test
	void testAssessmentNameIsNotNull() {
		assessmentUnderTest.setId(100);
		assessmentUnderTest.setName(null);
		assessmentUnderTest.setReleasedToStudents(true);

		when(assessmentRepository.save(ArgumentMatchers.any(Assessment.class)))
				.thenThrow(new IllegalArgumentException("name cannot be null"));

		assertThrows(IllegalArgumentException.class, () -> assessmentRepository.save(assessmentUnderTest));
	}

	@Test
	void testReleasedToStudentsIsNotNull() {
		assessmentUnderTest.setId(100);
		assessmentUnderTest.setName("testName");
		assessmentUnderTest.setReleasedToStudents(null);

		when(assessmentRepository.save(ArgumentMatchers.any(Assessment.class)))
				.thenThrow(new IllegalArgumentException("releasedToStudents cannot be null"));

		assertThrows(IllegalArgumentException.class, () -> assessmentRepository.save(assessmentUnderTest));
	}

	@Test
	void testAssessmentIdIsUnique() {
		assessmentUnderTest.setId(100);
		assessmentUnderTest.setName("textAssessment");
		assessmentUnderTest.setReleasedToStudents(false);

		Assessment assessment2 = new Coursework();
		assessment2.setId(100);
		assessment2.setName("textAssessment2");
		assessment2.setReleasedToStudents(false);

		when(assessmentRepository.save(assessmentUnderTest)).thenReturn(assessmentUnderTest);
		when(assessmentRepository.save(assessment2))
				.thenThrow(new IllegalStateException("duplicate ID"));

		assessmentRepository.save(assessmentUnderTest);

		assertThrows(IllegalStateException.class, () -> assessmentRepository.save(assessment2));
	}


	@Test
	void testModuleLoadForAssesments() {
		assessmentUnderTest.setId(100);
		assessmentUnderTest.setName("textAssessment");
		assessmentUnderTest.setReleasedToStudents(false);
		
		AssessmentModule linkingTable = new AssessmentModule();
		linkingTable.setId(8);
		linkingTable.setAssessment(assessmentUnderTest);;
		ArrayList<AssessmentModule> linkingTables = new ArrayList<AssessmentModule>();
		linkingTables.add(linkingTable);
		assessmentUnderTest.setModules(linkingTables);

		when(assessmentRepository.findById(100)).thenReturn(Optional.of(assessmentUnderTest));

		Assessment retrievedAssessment = assessmentRepository.findById(100).orElse(null);
		assertNotNull(retrievedAssessment);
		assertNotNull(retrievedAssessment.getModules());
	}

	@Test
	void deleteAssessment(){
		assessmentUnderTest.setId(100);
		assessmentUnderTest.setName("textAssessment");
		assessmentUnderTest.setReleasedToStudents(false);
		assessmentRepository.save(assessmentUnderTest);

		when(assessmentRepository.existsById(100)).thenReturn(true).thenReturn(false);
		when(assessmentRepository.findById(100)).thenReturn(Optional.of(assessmentUnderTest)).thenReturn(Optional.empty());

		assertTrue(assessmentRepository.existsById(100));
		assertNotNull(assessmentRepository.findById(100).orElse(null));

		assessmentRepository.deleteById(100);

		assertFalse(assessmentRepository.existsById(100));
		assertTrue(assessmentRepository.findById(100).isEmpty());
	}

}