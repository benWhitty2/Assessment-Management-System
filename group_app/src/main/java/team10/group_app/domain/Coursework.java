package team10.group_app.domain;

import jakarta.persistence.*;
import team10.group_app.dto.CourseworkDTO;
import java.time.LocalDateTime;

/*
 * Coursework entity representing a coursework assessment in the system.
 * id, deadline
 * Extends Assessment
 */
@Entity
public class Coursework extends Assessment {

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Column(nullable = false)
    private String specification;

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public CourseworkDTO toDTO() {
        CourseworkDTO dto = new CourseworkDTO();
        dto.setId(this.getId());
        dto.setSetter(this.getSetter());
        dto.setChecker(this.getChecker());
        dto.setName(this.getName());
        dto.setReleasedToStudents(this.getReleasedToStudents());
        dto.setDeadline(this.getDeadline());
        dto.setSpecification(this.getSpecification());
        return dto;
    }
}