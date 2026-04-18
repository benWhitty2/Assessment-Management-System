package team10.group_app.repository;

import java.util.List;
import java.util.Optional;
import team10.group_app.domain.User;
import team10.group_app.domain.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import team10.group_app.domain.ModuleStaff;

public interface ModuleStaffRepository extends JpaRepository<ModuleStaff, Integer> {
    
    List<ModuleStaff> findByUser(User user);

    List<ModuleStaff> findByModule(Module module);

    Optional<ModuleStaff> findByModuleRole(char moduleRole);

    Optional<ModuleStaff> findByUserAndModule(User user, Module module);
}
