package team10.group_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import team10.group_app.domain.Module;
// import team10.group_app.domain.ModuleStaff;
// import team10.group_app.domain.User;

public interface ModuleRepository extends JpaRepository<Module, Integer> {

    List<Module> findById(Module id);
    
}
