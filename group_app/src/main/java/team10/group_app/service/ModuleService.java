package team10.group_app.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import jakarta.transaction.Transactional;
import team10.group_app.domain.Module;
import team10.group_app.repository.ModuleRepository;

@Service
@Transactional
public class ModuleService {
    
    private final ModuleRepository moduleRepository;

    private static final String MODULE_NOT_FOUND = "Module does not exist.";

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public Module createModule(Module newModule) {
        return moduleRepository.save(newModule);
    }

    public List<Module> getModules() {
        return moduleRepository.findAll();
    }

    public Module getModule(int id) {
        return moduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, MODULE_NOT_FOUND));
    }

    public void deleteModule(int id) {
        if (!moduleRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, MODULE_NOT_FOUND);
        }
        moduleRepository.deleteById(id);
    }

    public Module updateModule(Module updatedModule, int id) {
        Module storedModule = getModule(id);
        storedModule.setUsers(updatedModule.getUsers());
        storedModule.setAssessments(updatedModule.getAssessments());
        return moduleRepository.save(storedModule);
    }
}
