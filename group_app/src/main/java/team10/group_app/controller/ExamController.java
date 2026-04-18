package team10.group_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team10.group_app.domain.Exam;
import team10.group_app.dto.ExamDTO;
import team10.group_app.service.ExamService;

import java.util.List;

@RestController
@RequestMapping("/exams")
public class ExamController {

    private final ExamService service;

    @Autowired
    public ExamController(ExamService service) {
        this.service = service;
    }

    @PostMapping({"", "/"})
    public ResponseEntity<ExamDTO> create(@RequestBody ExamDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createExam(dto.toEntity()).toDTO());
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<ExamDTO>> getAll() {
        return ResponseEntity.ok(service.getAllExams().stream()
                .map(Exam::toDTO)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(service.getExamById(id).toDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamDTO> update(@PathVariable int id, @RequestBody ExamDTO dto) {
        return ResponseEntity.ok(service.updateExam(dto.toEntity(), id).toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.deleteExam(id);
        return ResponseEntity.noContent().build();
    }
}
