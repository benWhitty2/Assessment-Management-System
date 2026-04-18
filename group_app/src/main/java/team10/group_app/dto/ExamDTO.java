package team10.group_app.dto;

import team10.group_app.domain.Exam;
import java.time.LocalDateTime;

public class ExamDTO extends AssessmentDTO {

    private LocalDateTime duration;
    private LocalDateTime startTime;
    private Boolean ongoing;
    private String feedback;
    private String response;

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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Exam toEntity() {
        Exam entity = new Exam();
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
        entity.setFeedback(this.feedback);
        entity.setResponse(this.response);
        return entity;
    }
}
