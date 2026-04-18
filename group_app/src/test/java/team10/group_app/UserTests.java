package team10.group_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import team10.group_app.domain.User;
import team10.group_app.repository.UserRepository;
import team10.group_app.service.UserService;
import team10.group_app.dto.UserSignupDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTests {

	@Autowired UserRepository userRepository;
	@Autowired UserService userService;
	@Autowired PasswordEncoder passwordEncoder;

	@BeforeEach
	void resetDatabase() {
		userRepository.deleteAll();
	}


	@Test
	void contextLoads() {
	}

	@Test
	void testNameIsNotNull() {
		User user = new User("bob", "password","ROLE_USER");
		user.setEmail("bob@example.com");

		assertNotNull(user.getUsername());
	}

	@Test
	void testUsernameIsUnique() {
		User u1 = new User("bob", passwordEncoder.encode("pass"), "ROLE_USER");
		u1.setEmail("bob@example.com");
		userRepository.save(u1);

		boolean exists = userRepository.findByUsername("bob").isPresent();
		assertTrue(exists);
	}


	@Test
	void testUsernameIsValid() {
		User user = new User("alice!", "pass","ROLE_USER");
		assertTrue(user.getUsername().length() >= 3);
	}


	@Test
	void testEmailIsValid() {
		User user = new User("bob", "pass", "ROLE_USER");
		user.setEmail("bob@example.com");

		assertTrue(user.getEmail().contains("@"));
	}

	@Test
	void testPasswordIsNotNull() {
		User user = new User("bob", passwordEncoder.encode("pass"),"ROLE_USER");
		assertNotNull(user.getPassword());
	}

	@Test
	void testPasswordIsHashedCorrectly() {
		String rawPassword = "pass123";
		String encodedPassword = passwordEncoder.encode(rawPassword);

		assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
	}

	@Test
	void testUserRoleAssignment() {
		User user = new User("alice", "pass", "ROLE_USER");
		assertEquals("ROLE_USER", user.getRole());
	}

	@Test
	void testSettersAndGetters() {
		User user = new User();
		user.setUsername("bob");
		user.setEmail("bob@example.com");
		user.setRole("ROLE_ADMIN");

		assertEquals("bob", user.getUsername());
		assertEquals("bob@example.com", user.getEmail());
		assertEquals("ROLE_ADMIN", user.getRole());
	}

	@Test
	void endToEndCreateUser() {
		UserSignupDTO dto = new UserSignupDTO();
		dto.setUsername("bob");
		dto.setPassword("password");
		dto.setEmail("bob@example.com");
		dto.setRole("ROLE_USER");

		var response = userService.signupNewUser(dto);

		assertEquals("bob", response.getUser().getUsername());
		assertNotNull(response.getToken());
	}

}
