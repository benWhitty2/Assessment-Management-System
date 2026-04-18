package team10.group_app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import team10.group_app.dto.AssessmentModuleDTO;

/*
 * AssessmentModule entity representing the association between Assessment and Module.
 * id
 * ManyToOne relationship with Assessment and Module
 */

@Entity
@Table(name = "assessmentModule")
public class AssessmentModule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private Assessment assessment;

    @ManyToOne
    private Module module;

    private Stage stage;

    // Constructor with parameters
    public AssessmentModule(Assessment assessment, Module module) {
        this.assessment = assessment;
        this.module = module;
        this.stage = Stage.CREATED;
    }

    public AssessmentModule() { // No arg constructor
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public AssessmentModuleDTO toDto() {
        AssessmentModuleDTO dto = new AssessmentModuleDTO();
        if (this.assessment != null) {
            dto.setAssessmentId(this.assessment.getId());
            dto.setAssessment(this.assessment);
        }
        if (this.module != null) {
            dto.setModuleId(this.module.getId());
        }
        if (this.id != null) {
            dto.setId(this.id);
        }
        dto.setStage(this.stage);
        return dto;
    }
}