package team10.group_app.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import team10.group_app.dto.UserDTO;

/*
 * User entity representing a user in the system.
 * id, username, email, active status, role
 * OneToMany relationship with ModuleStaff
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = true)
    private Boolean active;

    // Role is reperesented as an integer (0 = ... , 1 = ... , 2 = ...)
    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<ModuleStaff> modules;

    @OneToMany(mappedBy = "setter")
    private List<Assessment> assessmentSetter;

    @OneToMany(mappedBy = "externalExaminer")
    private List<Exam> externalExaminer;

    @OneToMany(mappedBy = "checker")
    private List<Assessment> assessmentChecker; 

    // Constructors

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User() { // Spring Data JPA requires a no arg constructor
    }

    public List<Assessment> getSetters(){
        return assessmentSetter;
    }

    public void setSetters(List<Assessment> assessmentSetter){
        this.assessmentSetter = assessmentSetter;
    }

    public List<Exam> getExternalExaminers(){
        return externalExaminer;
    }

     public void setExternalExaminers(List<Exam> externalExaminer){
        this.externalExaminer = externalExaminer;
    }


    public List<Assessment> getCheckers(){
        return assessmentChecker;
    }

    public void setSetter(List<Assessment> assessmentChecker){
        this.assessmentChecker = assessmentChecker;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ModuleStaff> getModules() {
        return modules;
    }

    public void setModules(List<ModuleStaff> modules) {
        this.modules = modules;
    }

    public UserDTO toDTO() {
        UserDTO dto = new UserDTO();
        dto.setId(this.id);
        dto.setUsername(this.username);
        dto.setEmail(this.email);
        dto.setActive(this.active);
        dto.setRole(this.role);
        return dto;
    }

}