package team10.group_app.domain;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import team10.group_app.dto.ModuleDTO;

// TODO: add functions for module name

@Entity
@Table(name = "module")
public class Module {

    // IIRC the PK for Module is the module code and is hence not autogenned
    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private Integer studyYear;

    @OneToMany(mappedBy = "module")
    // @JoinColumn(name = "moduleId")
    private List<ModuleStaff> users;

    @OneToMany(mappedBy = "module")
    // @JoinColumn(name = "moduleId")
    private List<AssessmentModule> assessments;

    // Contructor with parameters
    public Module(List<ModuleStaff> users, List<AssessmentModule> assessments) {
        this.users = users;
        this.assessments = assessments;
    }

    public Module() { // No arg constructor
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Integer studyYear) {
        this.studyYear = studyYear;
    }

    public List<ModuleStaff> getUsers() {
        return users;
    }

    public void setUsers(List<ModuleStaff> users) {
        this.users = users;
    }

    public List<AssessmentModule> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<AssessmentModule> assessments) {
        this.assessments = assessments;
    }

    public ModuleDTO toDto() {
        ModuleDTO dto = new ModuleDTO();
        if (this.users != null) {
            dto.setUsers(this.users.stream()
                    .map(team10.group_app.domain.ModuleStaff::toDto)
                    .collect(Collectors.toList()));
        }
        if (this.assessments != null) {
            dto.setAssessments(this.assessments.stream()
                    .map(team10.group_app.domain.AssessmentModule::toDto)
                    .collect(Collectors.toList()));
        }
        if (this.id != null) {
            dto.setId(this.id);
        }
        dto.setName(this.name);
        dto.setStudyYear(this.studyYear);
        return dto;
    }
}