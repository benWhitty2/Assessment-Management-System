package team10.group_app.dto;

import team10.group_app.domain.AssessmentModule;
import team10.group_app.domain.Assessment;
import team10.group_app.domain.Stage;
import java.util.List;

public class AssessmentModuleDTO {

    private Integer id;
    private Integer assessmentId;
    private Assessment assessment; // For display purposes (GET)
    private Integer moduleId;
    private Stage stage;
    private List<ProgressStepDTO> progressSteps;

    public AssessmentModuleDTO() { // No arg constructor
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Integer assessmentId) {
        this.assessmentId = assessmentId;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public List<ProgressStepDTO> getProgressSteps() {
        return progressSteps;
    }

    public void setProgressSteps(List<ProgressStepDTO> progressSteps) {
        this.progressSteps = progressSteps;
    }

    public AssessmentModule toEntity() {
        AssessmentModule entity = new AssessmentModule();
        if (this.id != null) {
            entity.setId(this.id);
        }
        entity.setStage(this.stage);
        // Relations are handled in the Service layer
        return entity;
    }
}
