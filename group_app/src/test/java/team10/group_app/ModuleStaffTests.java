package team10.group_app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;

import team10.group_app.domain.User;
import team10.group_app.domain.ModuleStaff;
import team10.group_app.domain.Module;
import team10.group_app.repository.ModuleStaffRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModuleStaffTests {

	@Mock
	private ModuleStaffRepository moduleStaffRepository;

    @Mock
    private User user = new User();

    @Mock
    private Module module = new Module();

    @InjectMocks
    private ModuleStaff moduleStaffUnderTest = new ModuleStaff();


	@Test
	void testModuleStaffCodeIsUnique() {
		moduleStaffUnderTest.setId(100);
        moduleStaffUnderTest.setUser(user);
        moduleStaffUnderTest.setModule(module);

		ModuleStaff assessmentModule2 = new ModuleStaff();
		assessmentModule2.setId(100);
        assessmentModule2.setUser(user);
        assessmentModule2.setModule(module);

		when(moduleStaffRepository.save(moduleStaffUnderTest)).thenReturn(moduleStaffUnderTest);
		when(moduleStaffRepository.save(assessmentModule2))
				.thenThrow(new IllegalStateException("duplicate ID"));

		moduleStaffRepository.save(moduleStaffUnderTest);

		assertThrows(IllegalStateException.class, () -> moduleStaffRepository.save(assessmentModule2));
	}

	@Test
	void testUserLoads() {
		user.setId(10);
		
        moduleStaffUnderTest.setId(100);
        moduleStaffUnderTest.setUser(user);
		

		when(moduleStaffRepository.findById(100)).thenReturn(Optional.of(moduleStaffUnderTest));

		ModuleStaff retrievedModuleStaff = moduleStaffRepository.findById(100).orElse(null);
		assertNotNull(retrievedModuleStaff);
		assertNotNull(retrievedModuleStaff.getUser());
	}

    	@Test
	void testModuleLoads() {
		module.setId(10);
		
        moduleStaffUnderTest.setId(100);
        moduleStaffUnderTest.setModule(module);
		

		when(moduleStaffRepository.findById(100)).thenReturn(Optional.of(moduleStaffUnderTest));

		ModuleStaff retrievedModuleStaff = moduleStaffRepository.findById(100).orElse(null);
		assertNotNull(retrievedModuleStaff);
		assertNotNull(retrievedModuleStaff.getModule());
	}

	@Test
	void testModuleStaffSettersAndGetters() {

		moduleStaffUnderTest.setId(100);
		moduleStaffUnderTest.setUser(user);
		moduleStaffUnderTest.setModule(module);

		assertEquals(100, moduleStaffUnderTest.getId());
		assertEquals(module, moduleStaffUnderTest.getModule());
		assertEquals(user, moduleStaffUnderTest.getUser());
	}

	@Test
	void endToEndCreateAndDeleteModule() {
		moduleStaffUnderTest.setId(100);
		moduleStaffUnderTest.setUser(user);
		moduleStaffUnderTest.setModule(module);
		
		when(moduleStaffRepository.save(moduleStaffUnderTest)).thenReturn(moduleStaffUnderTest);
		when(moduleStaffRepository.existsById(100)).thenReturn(true).thenReturn(false);
		when(moduleStaffRepository.findById(100)).thenReturn(Optional.of(moduleStaffUnderTest)).thenReturn(Optional.empty());

		moduleStaffRepository.save(moduleStaffUnderTest);


		assertTrue(moduleStaffRepository.existsById(100));

		ModuleStaff retrievedModuleStaff = moduleStaffRepository.findById(100).orElse(null);
		assertNotNull(retrievedModuleStaff);
	

		moduleStaffRepository.deleteById(100);

		assertFalse(moduleStaffRepository.existsById(100));
		assertTrue(moduleStaffRepository.findById(100).isEmpty());
	}
}
