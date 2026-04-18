package team10.group_app.dto;

import java.util.List;
import team10.group_app.domain.User;

import team10.group_app.domain.AssessmentModule;


public abstract class AssessmentDTO {

    private Integer id;
    private User setter;
    private User checker;
    private String name;
    private List<AssessmentModule> modules;
    private Boolean releasedToStudents;

    public AssessmentDTO() { //No arg constructor
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getSetter() {
        return setter;
    }

    public void setSetter(User setter) {
        this.setter = setter;
    }

    public User getChecker() {
        return checker;
    }

    public void setChecker(User checker) {
        this.checker = checker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AssessmentModule> getModules() {
        return modules;
    }

    public void setModules(List<AssessmentModule> modules) {
        this.modules = modules;
    }

    public Boolean getReleasedToStudents() {
        return releasedToStudents;
    }

    public void setReleasedToStudents(Boolean releasedToStudents) {
        this.releasedToStudents = releasedToStudents;
    }  
}
