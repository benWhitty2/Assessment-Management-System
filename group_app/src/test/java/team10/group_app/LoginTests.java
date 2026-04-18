package team10.group_app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import team10.group_app.domain.User;
import team10.group_app.dto.TokenDTO;
import team10.group_app.repository.UserRepository;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")

class LoginTests {

	@Autowired MockMvc mockMvc;
	@Autowired UserRepository userRepository;
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired ObjectMapper objectMapper;


	private User testUser;

	@BeforeEach
	void setup() {
		userRepository.deleteAll();

		testUser = new User("alice", passwordEncoder.encode("password"), "ROLE_USER");
		testUser.setEmail("alice@example.com");
		userRepository.save(testUser);
	}

	@Test
	void contextLoads() {
	}

	@Test
	void testValidLogin() throws Exception {
		MvcResult result = mockMvc.perform(post("/auth/login")
						.with(httpBasic(testUser.getUsername(), "password")))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(jsonPath("$", hasKey("token")))
				.andExpect(jsonPath("$", hasKey("user")))
				.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		TokenDTO responseTokenDto = objectMapper.readValue(responseBody, TokenDTO.class);

		assertEquals(testUser.getUsername(), responseTokenDto.getUser().getUsername());
	}


	@Test
	void testInvalidLogin() throws Exception {
		mockMvc.perform(post("/auth/login")
						.with(httpBasic(testUser.getUsername(), "wrongpass")))
				.andExpect(status().isUnauthorized());
	}

	// I beleive this is already covered in UserTests

	// @Test
	// void testSignup() throws Exception {
	// 	String json = """
    //     {
    //         "username": "bob",
    //         "password": "password",
    //         "email": "bob@example.com",
    //         "role": "ROLE_USER"
    //     }
    // """;

	// 	mockMvc.perform(post("/auth/signup")
	// 					.contentType("application/json")
	// 					.content(json))
	// 			.andExpect(status().isCreated())
	// 			.andExpect(jsonPath("$.token").exists())
	// 			.andExpect(jsonPath("$.user.username").value("bob"));
	// }

	// TODO: end to end tests for login that interfaces with the frontend

}
