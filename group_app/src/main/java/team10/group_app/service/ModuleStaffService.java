package team10.group_app.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import jakarta.transaction.Transactional;
import team10.group_app.domain.Module;
import team10.group_app.domain.ModuleStaff;
import team10.group_app.domain.User;
import team10.group_app.repository.ModuleRepository;
import team10.group_app.repository.ModuleStaffRepository;
import team10.group_app.repository.UserRepository;

@Service
@Transactional
public class ModuleStaffService {

    private final ModuleStaffRepository moduleStaffRepository;
    private final UserRepository userRepository;
    private final ModuleRepository moduleRepository;

    private static final String MODULE_STAFF_NOT_FOUND = "Module Staff does not exist.";
    private static final String USER_NOT_FOUND = "User does not exist.";
    private static final String MODULE_NOT_FOUND = "Module does not exist.";

    public ModuleStaffService(ModuleStaffRepository moduleStaffRepository, UserRepository userRepository,
            ModuleRepository moduleRepository) {
        this.moduleStaffRepository = moduleStaffRepository;
        this.userRepository = userRepository;
        this.moduleRepository = moduleRepository;
    }

    public ModuleStaff createModuleStaff(ModuleStaff newModuleStaff) {
        // Fetch User and Module from DB to ensure they are attached to the persistence
        // context
        // and to validate they exist.
        if (newModuleStaff.getUser() == null || newModuleStaff.getUser().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is required.");
        }
        if (newModuleStaff.getModule() == null || newModuleStaff.getModule().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Module ID is required.");
        }

        User user = userRepository.findById(newModuleStaff.getUser().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));

        Module module = moduleRepository.findById(newModuleStaff.getModule().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, MODULE_NOT_FOUND));

        newModuleStaff.setUser(user);
        newModuleStaff.setModule(module);

        return moduleStaffRepository.save(newModuleStaff);
    }

    public List<ModuleStaff> getModuleStaffs() {
        return moduleStaffRepository.findAll();
    }

    public ModuleStaff getModuleStaff(int id) {
        return moduleStaffRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, MODULE_STAFF_NOT_FOUND));
    }

    // Get all staff for a specific module
    public List<ModuleStaff> getStaffForModule(Module module) {
        return moduleStaffRepository.findByModule(module);
    }

    public void deleteModuleStaff(int id) {
        if (!moduleStaffRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, MODULE_STAFF_NOT_FOUND);
        }
        moduleStaffRepository.deleteById(id);
    }

    public ModuleStaff updateModuleStaff(ModuleStaff updatedModuleStaff, int id) {
        ModuleStaff storedModuleStaff = getModuleStaff(id);

        // Similarly fetch entities for update if they are provided
        if (updatedModuleStaff.getUser() != null && updatedModuleStaff.getUser().getId() != null) {
            User user = userRepository.findById(updatedModuleStaff.getUser().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));
            storedModuleStaff.setUser(user);
        }

        if (updatedModuleStaff.getModule() != null && updatedModuleStaff.getModule().getId() != null) {
            Module module = moduleRepository.findById(updatedModuleStaff.getModule().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, MODULE_NOT_FOUND));
            storedModuleStaff.setModule(module);
        }

        storedModuleStaff.setModuleRole(updatedModuleStaff.getModuleRole());
        return moduleStaffRepository.save(storedModuleStaff);
    }

    public List<Module> getModulesForUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));

        List<ModuleStaff> staffEntries = moduleStaffRepository.findByUser(user);
        return staffEntries.stream()
                .map(ModuleStaff::getModule)
                .distinct()
                .toList();
    }
}
