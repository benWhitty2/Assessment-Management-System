package team10.group_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team10.group_app.domain.Test;
import team10.group_app.dto.TestDTO;
import team10.group_app.service.TestService;

import java.util.List;

@RestController
@RequestMapping("/tests")
public class TestController {

    private final TestService service;

    @Autowired
    public TestController(TestService service) {
        this.service = service;
    }

    @PostMapping({"", "/"})
    public ResponseEntity<TestDTO> create(@RequestBody TestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createTest(dto.toEntity()).toDTO());
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<TestDTO>> getAll() {
        return ResponseEntity.ok(service.getAllTests().stream()
                .map(Test::toDTO)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(service.getTestById(id).toDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestDTO> update(@PathVariable int id, @RequestBody TestDTO dto) {
        return ResponseEntity.ok(service.updateTest(dto.toEntity(), id).toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.deleteTest(id);
        return ResponseEntity.noContent().build();
    }
}
