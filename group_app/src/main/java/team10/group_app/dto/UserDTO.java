package team10.group_app.dto;

import team10.group_app.domain.User;

public class UserDTO {

    private Integer id;
    private String username;
    private String email;
    private Boolean active;
    private String role;
    private String password;
    

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Convert DTO to entity
    public User toEntity() {
        User entity = new User();
        if (this.id != null) {
            entity.setId(this.id);
        }
        entity.setUsername(this.username);
        entity.setEmail(this.email);
        entity.setActive(this.active);
        entity.setRole(this.role);
        entity.setPassword(this.password);
        return entity;
    }
}
