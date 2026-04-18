package team10.group_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team10.group_app.domain.Coursework;
import team10.group_app.dto.CourseworkDTO;
import team10.group_app.service.CourseworkService;

import java.util.List;

@RestController
@RequestMapping("/courseworks")
public class CourseworkController {

    private final CourseworkService service;

    @Autowired
    public CourseworkController(CourseworkService service) {
        this.service = service;
    }

    @PostMapping({"", "/"})
    public ResponseEntity<CourseworkDTO> create(@RequestBody CourseworkDTO dto) {
        Coursework created = service.createCoursework(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(created.toDTO());
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<CourseworkDTO>> getAll() {
        return ResponseEntity.ok(service.getAllCourseworks().stream()
                .map(Coursework::toDTO)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseworkDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(service.getCourseworkById(id).toDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseworkDTO> update(@PathVariable int id, @RequestBody CourseworkDTO dto) {
        return ResponseEntity.ok(service.updateCoursework(dto.toEntity(), id).toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.deleteCoursework(id);
        return ResponseEntity.noContent().build();
    }
}
