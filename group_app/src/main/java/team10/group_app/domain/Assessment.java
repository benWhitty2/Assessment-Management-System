package team10.group_app.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/*
 * Assessment entity representing an assessment in the system.
 * setter, checker, name
 * OneToMany relationship with AssessmentModule
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Coursework.class, name = "Coursework"),
        @JsonSubTypes.Type(value = Exam.class, name = "Exam"),
        @JsonSubTypes.Type(value = Test.class, name = "Test")
})

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;

    // @Column(nullable = false)
    @ManyToOne
    @JsonIgnore // To mitigate Circular Reference Problem
    private User setter;

    // @Column(nullable = false)
    @ManyToOne
    @JsonIgnore
    private User checker;

    /*
     * Discuessed in the meeting, do we need Assesment module, or can we use the
     * built in many to many
     */
    @OneToMany(mappedBy = "assessment")
    @JsonIgnore
    private List<AssessmentModule> modules;

    @Column(nullable = false)
    private Boolean releasedToStudents;

    // Constructor with parameters
    public Assessment(User setter, User checker, String name) {
        this.setter = setter;
        this.checker = checker;
        this.name = name;
    }

    // No arg constructor
    public Assessment() {
    }

    // Getters and setters
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
