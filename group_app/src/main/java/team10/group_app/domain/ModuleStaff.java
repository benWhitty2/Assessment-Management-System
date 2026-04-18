package team10.group_app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import team10.group_app.dto.ModuleStaffDTO;

@Entity
@Table(name = "moduleStaff")
public class ModuleStaff {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;// could we replce this with a tuple containing user and module?
    // yes assuming a user can only have one role per module (i think this is the
    // case)

    @ManyToOne
    private User user;

    @ManyToOne
    private Module module;
    private char moduleRole;

    // Constructor with parameters
    public ModuleStaff(User user, Module module, char moduleRole) {
        this.user = user;
        this.module = module;
        this.moduleRole = moduleRole;
    }

    public ModuleStaff() {
    } // No arg constructor

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public char getModuleRole() {
        return moduleRole;
    }

    public void setModuleRole(char moduleRole) {
        this.moduleRole = moduleRole;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public ModuleStaffDTO toDto() {
        ModuleStaffDTO dto = new ModuleStaffDTO();
        if (this.user != null) {
            dto.setUser(this.user.toDTO());
        }

        // To avoid infinite recursion (Module -> Staff -> Module), we use a shallow
        // ModuleDTO.
        if (this.module != null) {
            team10.group_app.dto.ModuleDTO moduleDTO = new team10.group_app.dto.ModuleDTO();
            moduleDTO.setId(this.module.getId());
            moduleDTO.setName(this.module.getName());
            moduleDTO.setStudyYear(this.module.getStudyYear());
            // IMPORTANT: Do NOT set users or assessments here to prevent recursion
            dto.setModule(moduleDTO);
        }

        dto.setModuleRole(moduleRole);
        if (this.id != null) {
            dto.setId(this.id);
        }
        return dto;
    }
}