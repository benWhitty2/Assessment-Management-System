package team10.group_app.controller;

import org.springframework.web.bind.annotation.*;
import team10.group_app.domain.AssessmentModule;
import team10.group_app.dto.AssessmentModuleDTO;
import team10.group_app.service.AssessmentModuleService;
import team10.group_app.dto.ProgressStepDTO;

import java.util.List;

@RestController
@RequestMapping("/assessment-modules")
public class AssessmentModuleController {

    private final AssessmentModuleService assessmentModuleService;

    public AssessmentModuleController(AssessmentModuleService assessmentModuleService) {
        this.assessmentModuleService = assessmentModuleService;
    }

    // Get assessmentModule by ID
    @GetMapping("/{id}")
    public AssessmentModuleDTO getAssessmentModule(@PathVariable int id) {
        AssessmentModule assessmentModule = assessmentModuleService.getAssessmentModuleById(id);
        AssessmentModuleDTO dto = assessmentModule.toDto();

        // Calculate progress steps
        List<ProgressStepDTO> steps = assessmentModuleService.calculateProgressSteps(assessmentModule.getStage());
        dto.setProgressSteps(steps);

        return dto;
    }

    @PutMapping("/{id}")
    public AssessmentModuleDTO updateAssessmentModule(@PathVariable int id, @RequestBody AssessmentModuleDTO dto) {
        AssessmentModule updated = assessmentModuleService.updateAssessmentModule(id, dto.toEntity(),
                dto.getAssessmentId(), dto.getModuleId());
        return updated.toDto();
    }

    @PostMapping
    public AssessmentModuleDTO createAssessmentModule(@RequestBody AssessmentModuleDTO dto) {
        System.out.println("Received createAssessmentModule request: " + dto);
        System.out.println("AssessmentID: " + dto.getAssessmentId() + ", ModuleID: " + dto.getModuleId());
        AssessmentModule created = assessmentModuleService.createAssessmentModule(dto.toEntity(), dto.getAssessmentId(),
                dto.getModuleId());
        return created.toDto();
    }
}
