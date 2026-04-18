package team10.group_app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import team10.group_app.domain.AssessmentModule;
import team10.group_app.domain.Stage;
import team10.group_app.dto.ProgressStepDTO;
import team10.group_app.repository.AssessmentModuleRepository;

@Service
public class AssessmentModuleService {

    private final AssessmentModuleRepository repository;
    private final team10.group_app.repository.AssessmentRepository assessmentRepository;
    private final team10.group_app.repository.ModuleRepository moduleRepository;
    private static final String ASSESSMENT_NOT_FOUND = "Assessment does not exist";
    private static final String MODULE_NOT_FOUND = "Module does not exist";

    public AssessmentModuleService(AssessmentModuleRepository repository,
            team10.group_app.repository.AssessmentRepository assessmentRepository,
            team10.group_app.repository.ModuleRepository moduleRepository) {
        this.repository = repository;
        this.assessmentRepository = assessmentRepository;
        this.moduleRepository = moduleRepository;
    }

    public AssessmentModule createAssessmentModule(AssessmentModule newAssessment, Integer assessmentId,
            Integer moduleId) {
        System.out.println("Service: createAssessmentModule. AssessmentId=" + assessmentId + ", ModuleId=" + moduleId);
        if (assessmentId != null) {
            team10.group_app.domain.Assessment assessment = assessmentRepository.findById(assessmentId)
                    .orElseThrow(() -> {
                        System.out.println("Assessment NOT FOUND: " + assessmentId);
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, ASSESSMENT_NOT_FOUND);
                    });
            newAssessment.setAssessment(assessment);
        }
        if (moduleId != null) {
            team10.group_app.domain.Module module = moduleRepository.findById(moduleId)
                    .orElseThrow(() -> {
                        System.out.println("Module NOT FOUND: " + moduleId);
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, MODULE_NOT_FOUND);
                    });
            newAssessment.setModule(module);
        }
        return repository.save(newAssessment);
    }

    public List<AssessmentModule> getAllAssessmentModules() {
        return repository.findAll();
    }

    public AssessmentModule getAssessmentModuleById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ASSESSMENT_NOT_FOUND));
    }

    public AssessmentModule updateAssessmentModule(int id, AssessmentModule updatedAssessment, Integer assessmentId,
            Integer moduleId) {
        try {
            AssessmentModule stored = getAssessmentModuleById(id);
            if (assessmentId != null) {
                team10.group_app.domain.Assessment assessment = assessmentRepository.findById(assessmentId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ASSESSMENT_NOT_FOUND));
                stored.setAssessment(assessment);
            }
            if (moduleId != null) {
                team10.group_app.domain.Module module = moduleRepository.findById(moduleId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, MODULE_NOT_FOUND));
                stored.setModule(module);
            }
            if (updatedAssessment.getStage() != null) {
                stored.setStage(updatedAssessment.getStage());
            }
            return repository.save(stored);
        } catch (Exception e) {
            System.err.println("Error updating AssessmentModule ID: " + id);
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteAssessmentModule(int id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ASSESSMENT_NOT_FOUND);
        }
        repository.deleteById(id);
    }

    public List<ProgressStepDTO> calculateProgressSteps(Stage currentStage) {
        List<ProgressStepDTO> steps = new ArrayList<>();
        String[] stageOrder = { "CREATED", "RELEASED", "SUBMITTED", "MARKED", "MODERATED", "COMPLETED" };

        steps.add(new ProgressStepDTO(1, "Specification Created", getStatus(currentStage, "CREATED", stageOrder),
                "The specification of the assessment is created.", "CREATED"));
        steps.add(new ProgressStepDTO(2, "Specification Checked", getStatus(currentStage, "CREATED", stageOrder),
                "Checked and verified.", "CREATED"));
        steps.add(new ProgressStepDTO(3, "Modifications and Approval", getStatus(currentStage, "CREATED", stageOrder),
                "Awaiting final sign-off.", "CREATED"));
        steps.add(new ProgressStepDTO(4, "Released to Students", getStatus(currentStage, "RELEASED", stageOrder),
                "Released for students to attempt.", "RELEASED"));
        steps.add(new ProgressStepDTO(5, "Marking", getStatus(currentStage, "SUBMITTED", stageOrder),
                "Waiting for submission deadline.", "SUBMITTED"));
        steps.add(new ProgressStepDTO(6, "Moderation", getStatus(currentStage, "MARKED", stageOrder),
                "Internal moderation process.", "MARKED"));
        steps.add(new ProgressStepDTO(7, "Feedback Returned", getStatus(currentStage, "MODERATED", stageOrder),
                "Feedback release to students.", "MODERATED"));

        return steps;
    }

    private String getStatus(Stage currentStage, String stepStageName, String[] stageOrder) {
        int currentIndex = -1;
        int stepIndex = -1;

        for (int i = 0; i < stageOrder.length; i++) {
            if (stageOrder[i].equals(currentStage.name()))
                currentIndex = i;
            if (stageOrder[i].equals(stepStageName))
                stepIndex = i;
        }

        if (currentIndex > stepIndex)
            return "Completed";
        if (currentIndex == stepIndex)
            return "In Progress";
        return "Pending";
    }

}
