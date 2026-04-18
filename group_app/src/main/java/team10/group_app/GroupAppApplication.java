package team10.group_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import team10.group_app.config.RsaKeyProperties;
// import team10.group_app.domain.Coursework;
import team10.group_app.domain.User;
import team10.group_app.repository.UserRepository;

import com.github.javafaker.Faker;
import java.time.LocalDateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class GroupAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(GroupAppApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository,
			team10.group_app.repository.ModuleRepository moduleRepository,
			team10.group_app.repository.ModuleStaffRepository moduleStaffRepository,
			team10.group_app.repository.AssessmentRepository assessmentRepository,
			team10.group_app.repository.AssessmentModuleRepository assessmentModuleRepository,
			PasswordEncoder passwordEncoder) {
		return args -> {
			Faker faker = new Faker();

			// Create a fixed admin user for testing
			if (userRepository.findByUsername("admin").isEmpty()) {
				User admin = new User("admin", passwordEncoder.encode("password?"), "0");
				admin.setEmail("admin@sheffield.ac.uk");
				admin.setActive(true);
				userRepository.save(admin);
				System.out.println("Generated Fixed User: admin | Password: password?");
			}

			if (userRepository.findByUsername("support").isEmpty()) {
				User support = new User("support", passwordEncoder.encode("password?"), "1");
				support.setEmail("support@sheffield.ac.uk");
				support.setActive(true);
				userRepository.save(support);
				System.out.println("Generated Fixed User: support | Password: password?");
			}

			if (userRepository.findByUsername("academic").isEmpty()) {
				User academic = new User("academic", passwordEncoder.encode("password?"), "2");
				academic.setEmail("academic@sheffield.ac.uk");
				academic.setActive(true);
				userRepository.save(academic);
				System.out.println("Generated Fixed User: academic | Password: password?");
			}

			if (userRepository.findByUsername("external").isEmpty()) {
				User external = new User("external", passwordEncoder.encode("password?"), "3");
				external.setEmail("external@sheffield.ac.uk");
				external.setActive(true);
				userRepository.save(external);
				System.out.println("Generated Fixed User: external | Password: password?");
			}

			// Seed Module
			team10.group_app.domain.Module module = new team10.group_app.domain.Module();
			module.setId(1001);
			module.setName("Software Engineering");
			module.setStudyYear(2025);
			moduleRepository.save(module);
			System.out.println("Generated Module: COM1001");

			team10.group_app.domain.Module module2 = new team10.group_app.domain.Module();
			module2.setId(1002);
			module2.setName("Data Science");
			module2.setStudyYear(2025);
			moduleRepository.save(module2);
			System.out.println("Generated Module: COM1002");

			for (int i = 0; i < 10; i++) {
				String username = faker.name().username();
				String password = passwordEncoder.encode("password?");
				String role = String.valueOf(faker.number().numberBetween(1, 3));
				String email = username + "@sheffield.ac.uk";

				User user = new User(username, password, role);
				user.setEmail(email);
				user.setActive(true);

				team10.group_app.domain.ModuleStaff moduleStaff = new team10.group_app.domain.ModuleStaff();
				moduleStaff.setUser(user);
				moduleStaff.setModule(module);
				if(i ==0 ){
					moduleStaff.setModuleRole('L');
				}else{
					moduleStaff.setModuleRole('S');
				}
				team10.group_app.domain.ModuleStaff moduleStaff2 = new team10.group_app.domain.ModuleStaff();
				moduleStaff2.setUser(user);
				moduleStaff2.setModule(module2);
				if(i ==0 ){
					moduleStaff2.setModuleRole('L');
				}else{
					moduleStaff2.setModuleRole('S');
				}
				try {
					userRepository.save(user);
					moduleStaffRepository.save(moduleStaff);
					moduleStaffRepository.save(moduleStaff2);
					
				} catch (Exception e) {
					// Ignore duplicates
				}
	
			}
			System.out.println("Users Generated");

			// Seed Coursework
			if (assessmentRepository.findByName("Assignment 1").isEmpty()) {
				team10.group_app.domain.Coursework coursework = new team10.group_app.domain.Coursework();
				coursework.setName("Assignment 1");
				coursework.setDeadline(java.time.LocalDateTime.now().plusDays(7));
				coursework.setSpecification("Complete the assignment.");
				coursework.setReleasedToStudents(false);
				assessmentRepository.save(coursework);
				System.out.println("Generated Assessment: Assignment 1");

				// Seed AssessmentModule (linking Assessment and Module)
				team10.group_app.domain.AssessmentModule am = new team10.group_app.domain.AssessmentModule(coursework,
						module);
				am.setStage(team10.group_app.domain.Stage.RELEASED); // Set to RELEASED
				assessmentModuleRepository.save(am);
				System.out.println("Generated AssessmentModule linking COM1001 and Assignment 1 with ID: " + am.getId());
			}

			if (assessmentRepository.findByName("Assignment 2").isEmpty()) {
				team10.group_app.domain.Coursework coursework2 = new team10.group_app.domain.Coursework();
				coursework2.setName("Assignment 2");
				coursework2.setDeadline(java.time.LocalDateTime.now().plusDays(7));
				coursework2.setSpecification("Complete the assignment.");
				coursework2.setReleasedToStudents(false);
				assessmentRepository.save(coursework2);
				System.out.println("Generated Assessment: Assignment 2");

				team10.group_app.domain.AssessmentModule am2 = new team10.group_app.domain.AssessmentModule(coursework2,
					module2);
				am2.setStage(team10.group_app.domain.Stage.MARKED); // Set to MARKED
				assessmentModuleRepository.save(am2);
				System.out.println("Generated AssessmentModule linking COM1002 and Assignment 2 with ID: " + am2.getId());
			}

			// Seed Exam
			if (assessmentRepository.findByName("Exam finale").isEmpty()) {
				team10.group_app.domain.Exam exam = new team10.group_app.domain.Exam();
				exam.setName("Exam finale");
				exam.setStartTime(LocalDateTime.now().plusDays(7));
				exam.setDuration(LocalDateTime.now().plusDays(7).plusHours(2)); // Assuming duration is end time or similar,
																				// matching type
				exam.setOngoing(false);
				exam.setReleasedToStudents(false);
				assessmentRepository.save(exam);
				System.out.println("Generated Assessment: Exam finale");

				// Link Exam to Module 1001
				team10.group_app.domain.AssessmentModule amExam = new team10.group_app.domain.AssessmentModule(exam,
						module);
				amExam.setStage(team10.group_app.domain.Stage.CREATED); // Set to CREATED
				assessmentModuleRepository.save(amExam);
				System.out.println("Generated AssessmentModule linking COM1001 and Exam finale with ID: " + amExam.getId());
			}
		};
	}

}
