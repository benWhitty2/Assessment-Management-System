package team10.group_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team10.group_app.domain.Module;
import team10.group_app.dto.ModuleDTO;
import team10.group_app.service.ModuleService;
import team10.group_app.service.ModuleStaffService;

@RestController
@RequestMapping("/modules")
public class ModuleController {

    private final ModuleService moduleService;
    private final ModuleStaffService moduleStaffService;

    @Autowired
    public ModuleController(ModuleService moduleService, ModuleStaffService moduleStaffService) {
        this.moduleService = moduleService;
        this.moduleStaffService = moduleStaffService;
    }

    @PostMapping({ "/", "" })
    public ResponseEntity<ModuleDTO> createModule(@RequestBody ModuleDTO module) {
        Module createdModule = moduleService.createModule(module.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdModule.toDto());
    }

    @GetMapping({ "/", "" })
    public ResponseEntity<List<ModuleDTO>> getModules(@RequestParam(required = false) Integer userId) {
        if (userId != null) {
            return ResponseEntity.status(HttpStatus.OK).body(moduleStaffService.getModulesForUser(userId)
                    .stream()
                    .map(Module::toDto)
                    .toList());
        }
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.getModules()
                .stream()
                .map(Module::toDto)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuleDTO> getModule(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.getModule(id).toDto());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable int id) {
        moduleService.deleteModule(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModuleDTO> updateModule(@PathVariable int id, @RequestBody ModuleDTO updatedModule) {
        return ResponseEntity.ok(moduleService.updateModule(updatedModule.toEntity(), id).toDto());
    }
}
