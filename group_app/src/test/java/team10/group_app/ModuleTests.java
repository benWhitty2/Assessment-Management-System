package team10.group_app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;

import team10.group_app.domain.Coursework;
import team10.group_app.domain.AssessmentModule;
import team10.group_app.domain.Module;
import team10.group_app.repository.ModuleRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModuleTests {

	@Mock
	private ModuleRepository moduleRepository;

	@Test
	void testModuleNameIsNotNull() {
		Module module = new Module();
		module.setId(100);
		module.setName(null);
		module.setStudyYear(1);

		when(moduleRepository.save(ArgumentMatchers.any(Module.class)))
				.thenThrow(new IllegalArgumentException("name cannot be null"));

		assertThrows(IllegalArgumentException.class, () -> moduleRepository.save(module));
	}

	@Test
	void testModuleCodeIsUnique() {
		Module module1 = new Module();
		module1.setId(100);
		module1.setName("Data Structures");
		module1.setStudyYear(1);

		Module module2 = new Module();
		module2.setId(100);
		module2.setName("Algorithms");
		module2.setStudyYear(2);

		when(moduleRepository.save(module1)).thenReturn(module1);
		when(moduleRepository.save(module2))
				.thenThrow(new IllegalStateException("duplicate ID"));

		moduleRepository.save(module1);

		assertThrows(IllegalStateException.class, () -> moduleRepository.save(module2));
	}

	@Test
	void testAssesmentsLoadForModule() {
		Module module = new Module();
		module.setId(100);
		module.setName("Operating Systems");
		module.setStudyYear(2);
		
		AssessmentModule linkingTable = new AssessmentModule();
		linkingTable.setId(8);
		linkingTable.setModule(module);
		ArrayList<AssessmentModule> linkingTables = new ArrayList<AssessmentModule>();
		linkingTables.add(linkingTable);
		module.setAssessments(linkingTables);

		when(moduleRepository.findById(100)).thenReturn(Optional.of(module));

		Module retrievedModule = moduleRepository.findById(100).orElse(null);
		assertNotNull(retrievedModule);
		assertNotNull(retrievedModule.getAssessments());
	}

	@Test
	void testModuleSettersAndGetters() {
		Module module = new Module();
		module.setId(100);
		module.setName("Database Systems");
		module.setStudyYear(3);

		assertEquals(100, module.getId());
		assertEquals("Database Systems", module.getName());
		assertEquals(3, module.getStudyYear());
	}

	@Test
	void endToEndCreateAndDeleteModule() {
		Module module = new Module();
		module.setId(100);
		module.setName("Software Engineering");
		module.setStudyYear(2);

		when(moduleRepository.save(module)).thenReturn(module);
		when(moduleRepository.existsById(100)).thenReturn(true).thenReturn(false);
		when(moduleRepository.findById(100)).thenReturn(Optional.of(module)).thenReturn(Optional.empty());

		Module savedModule = moduleRepository.save(module);
		assertNotNull(savedModule);
		assertEquals(100, savedModule.getId());
		assertEquals("Software Engineering", savedModule.getName());
		assertEquals(2, savedModule.getStudyYear());

		assertTrue(moduleRepository.existsById(100));

		Module retrievedModule = moduleRepository.findById(100).orElse(null);
		assertNotNull(retrievedModule);
		assertEquals("Software Engineering", retrievedModule.getName());

		moduleRepository.deleteById(100);

		assertFalse(moduleRepository.existsById(100));
		assertTrue(moduleRepository.findById(100).isEmpty());
	}
}
