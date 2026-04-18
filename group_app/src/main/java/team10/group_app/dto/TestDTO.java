package team10.group_app.dto;

import team10.group_app.domain.Test;
import java.time.LocalDateTime;

public class TestDTO extends AssessmentDTO {

    private LocalDateTime duration;
    private LocalDateTime startTime;
    private Boolean ongoing;
    private Boolean autograded;

    public LocalDateTime getDuration() {
        return duration;
    }

    public void setDuration(LocalDateTime duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Boolean getOngoing() {
        return ongoing;
    }

    public void setOngoing(Boolean ongoing) {
        this.ongoing = ongoing;
    }

    public Boolean getAutograded() {
        return autograded;
    }

    public void setAutograded(Boolean autograded) {
        this.autograded = autograded;
    }

    public Test toEntity() {
        Test entity = new Test();
        if (getId() != null) {
            entity.setId(getId());
        }
        entity.setSetter(getSetter());
        entity.setChecker(getChecker());
        entity.setName(getName());
        entity.setReleasedToStudents(getReleasedToStudents());
        entity.setDuration(this.duration);
        entity.setStartTime(this.startTime);
        entity.setOngoing(this.ongoing);
        entity.setAutograded(this.autograded);
        return entity;
    }
}
