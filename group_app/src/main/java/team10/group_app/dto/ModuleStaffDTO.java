package team10.group_app.dto;

import team10.group_app.domain.ModuleStaff;

public class ModuleStaffDTO {

    private Integer id;
    private UserDTO user;
    private ModuleDTO module;
    private char moduleRole;

    public ModuleStaffDTO() { // No arg contstructor
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ModuleDTO getModule() {
        return module;
    }

    public void setModule(ModuleDTO module) {
        this.module = module;
    }

    public char getModuleRole() {
        return moduleRole;
    }

    public void setModuleRole(char moduleRole) {
        this.moduleRole = moduleRole;
    }

    public ModuleStaff toEntity() {
        ModuleStaff entity = new ModuleStaff();
        if (this.user != null) {
            entity.setUser(this.user.toEntity());
        }
        if (this.module != null) {
            entity.setModule(this.module.toEntity());
        }
        entity.setModuleRole(this.moduleRole);
        if (this.id != null) {
            entity.setId(this.id);
        }
        return entity;
    }
}
