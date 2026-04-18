package team10.group_app.dto;

import team10.group_app.domain.Coursework;
import java.time.LocalDateTime;

public class CourseworkDTO extends AssessmentDTO {

    private LocalDateTime deadline;
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

    public Coursework toEntity() {
        Coursework entity = new Coursework();
        if (getId() != null) {
            entity.setId(getId());
        }
        entity.setSetter(getSetter());
        entity.setChecker(getChecker());
        entity.setName(getName());
        entity.setReleasedToStudents(getReleasedToStudents());
        entity.setDeadline(this.deadline);
        entity.setSpecification(this.specification);
        return entity;
    }
}
