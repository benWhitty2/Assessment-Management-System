package team10.group_app.dto;

import java.util.List;

import team10.group_app.domain.AssessmentModule;
import team10.group_app.domain.Module;
import team10.group_app.domain.ModuleStaff;

public class ModuleDTO {

    private Integer id;
    private List<ModuleStaffDTO> users;
    private List<AssessmentModuleDTO> assessments;
    private String name;
    private Integer studyYear;

    public ModuleDTO() { // No arg contstructor
    }

    // Getters and setters
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ModuleStaffDTO> getUsers() {
        return users;
    }

    public void setUsers(List<ModuleStaffDTO> users) {
        this.users = users;
    }

    public List<AssessmentModuleDTO> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<AssessmentModuleDTO> assessments) {
        this.assessments = assessments;
    }

    public Module toEntity() {
        Module entity = new Module();
        if (this.users != null) {
            entity.setUsers(this.users.stream()
                    .map(team10.group_app.dto.ModuleStaffDTO::toEntity)
                    .collect(java.util.stream.Collectors.toList()));
        }
        if (this.assessments != null) {
            entity.setAssessments(this.assessments.stream()
                    .map(team10.group_app.dto.AssessmentModuleDTO::toEntity)
                    .collect(java.util.stream.Collectors.toList()));
        }
        if (this.id != null) {
            entity.setId(this.id);
        }
        entity.setName(this.name);
        entity.setStudyYear(this.studyYear);
        return entity;
    }
}
