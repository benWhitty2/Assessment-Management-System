package team10.group_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team10.group_app.domain.ModuleStaff;
import team10.group_app.dto.ModuleStaffDTO;
import team10.group_app.service.ModuleStaffService;

@RestController
@RequestMapping("/modulestaff")
public class ModuleStaffController {

    private final ModuleStaffService moduleStaffService;

    @Autowired
    public ModuleStaffController(ModuleStaffService moduleStaffService) {
        this.moduleStaffService = moduleStaffService;
    }

    @PostMapping({"/", ""})
    public ResponseEntity<ModuleStaffDTO> createModule(@RequestBody ModuleStaffDTO moduleStaff) {
        ModuleStaff createdModuleStaff = moduleStaffService.createModuleStaff(moduleStaff.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdModuleStaff.toDto());
    }

    @GetMapping({"/", ""})
    public ResponseEntity<List<ModuleStaffDTO>> getModuleStaffs() {
        return ResponseEntity.status(HttpStatus.OK).body(moduleStaffService.getModuleStaffs()
                    .stream()
                    .map(ModuleStaff::toDto)
                    .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuleStaffDTO> getModuleStaff(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(moduleStaffService.getModuleStaff(id).toDto());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModuleStaff(@PathVariable int id) {
        moduleStaffService.deleteModuleStaff(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModuleStaffDTO> updateModuleStaff(@PathVariable int id, @RequestBody ModuleStaffDTO updatedModuleStaff) {
        return ResponseEntity.ok(moduleStaffService.updateModuleStaff(updatedModuleStaff.toEntity(), id).toDto());
    }
}
