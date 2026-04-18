package team10.group_app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import team10.group_app.domain.Coursework;
import team10.group_app.domain.AssessmentModule;
import team10.group_app.domain.Module;
import team10.group_app.repository.AssessmentModuleRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssessmentModuleTests {

	@Mock
	private AssessmentModuleRepository assessmentModuleRepository;

    @Mock
    private Coursework coursework = new Coursework();

    @Mock
    private Module module = new Module();

    @InjectMocks
    private AssessmentModule assessmentModuleUnderTest = new AssessmentModule();


	@Test
	void testAssessmentModuleCodeIsUnique() {
		assessmentModuleUnderTest.setId(100);
        assessmentModuleUnderTest.setAssessment(coursework);
        assessmentModuleUnderTest.setModule(module);

		AssessmentModule assessmentModule2 = new AssessmentModule();
		assessmentModule2.setId(100);
        assessmentModule2.setAssessment(coursework);
        assessmentModule2.setModule(module);

		when(assessmentModuleRepository.save(assessmentModuleUnderTest)).thenReturn(assessmentModuleUnderTest);
		when(assessmentModuleRepository.save(assessmentModule2))
				.thenThrow(new IllegalStateException("duplicate ID"));

		assessmentModuleRepository.save(assessmentModuleUnderTest);

		assertThrows(IllegalStateException.class, () -> assessmentModuleRepository.save(assessmentModule2));
	}

	@Test
	void testAssessmentLoads() {
		coursework.setId(10);
		
        assessmentModuleUnderTest.setId(100);
        assessmentModuleUnderTest.setAssessment(coursework);
		

		when(assessmentModuleRepository.findById(100)).thenReturn(Optional.of(assessmentModuleUnderTest));

		AssessmentModule retrievedAssessmentModule = assessmentModuleRepository.findById(100).orElse(null);
		assertNotNull(retrievedAssessmentModule);
		assertNotNull(retrievedAssessmentModule.getAssessment());
	}

    	@Test
	void testModuleLoads() {
		module.setId(10);
		
        assessmentModuleUnderTest.setId(100);
        assessmentModuleUnderTest.setModule(module);
		

		when(assessmentModuleRepository.findById(100)).thenReturn(Optional.of(assessmentModuleUnderTest));

		AssessmentModule retrievedAssessmentModule = assessmentModuleRepository.findById(100).orElse(null);
		assertNotNull(retrievedAssessmentModule);
		assertNotNull(retrievedAssessmentModule.getModule());
	}

	@Test
	void testAssessmentModuleSettersAndGetters() {

		assessmentModuleUnderTest.setId(100);
		assessmentModuleUnderTest.setAssessment(coursework);
		assessmentModuleUnderTest.setModule(module);

		assertEquals(100, assessmentModuleUnderTest.getId());
		assertEquals(module, assessmentModuleUnderTest.getModule());
		assertEquals(coursework, assessmentModuleUnderTest.getAssessment());
	}

	@Test
	void endToEndCreateAndDeleteModule() {
		assessmentModuleUnderTest.setId(100);
		assessmentModuleUnderTest.setAssessment(coursework);
		assessmentModuleUnderTest.setModule(module);
		
		when(assessmentModuleRepository.save(assessmentModuleUnderTest)).thenReturn(assessmentModuleUnderTest);
		when(assessmentModuleRepository.existsById(100)).thenReturn(true).thenReturn(false);
		when(assessmentModuleRepository.findById(100)).thenReturn(Optional.of(assessmentModuleUnderTest)).thenReturn(Optional.empty());

		assessmentModuleRepository.save(assessmentModuleUnderTest);


		assertTrue(assessmentModuleRepository.existsById(100));

		AssessmentModule retrievedAssessmentModule = assessmentModuleRepository.findById(100).orElse(null);
		assertNotNull(retrievedAssessmentModule);
	

		assessmentModuleRepository.deleteById(100);

		assertFalse(assessmentModuleRepository.existsById(100));
		assertTrue(assessmentModuleRepository.findById(100).isEmpty());
	}
}
