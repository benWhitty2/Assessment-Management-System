package team10.group_app.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import team10.group_app.dto.ExamDTO;

@Entity
//@Table(name = "exam")
public class Exam extends Assessment{


    @Column(nullable = false)
    private LocalDateTime duration;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private Boolean ongoing;

    @ManyToOne
    @JsonIgnore
    private User externalExaminer;

    private String feedback;

    private String response;

    public Exam() { // Spring Data JPA requires a no arg constructor
    }

    public User getExternalExaminer(){
        return externalExaminer;
    }

    public void setExternalExaminer(User externalExaminer){
        this.externalExaminer = externalExaminer;
    }

    public String getFeedback(){
        return feedback;
    }

    public void setFeedback(String feedback){
        this.feedback = feedback;
    }

    public String getResponse(){
        return response;
    }

    public void setResponse(String response){
        this.response = response;
    }

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


    public ExamDTO toDTO() {
        ExamDTO dto = new ExamDTO();
        dto.setId(this.getId());
        dto.setSetter(this.getSetter());
        dto.setChecker(this.getChecker());
        dto.setName(this.getName());
        dto.setReleasedToStudents(this.getReleasedToStudents());
        dto.setDuration(this.duration);
        dto.setStartTime(this.startTime);
        dto.setOngoing(this.ongoing);
        dto.setFeedback(this.feedback);
        dto.setResponse(this.response);
        return dto;
    }
}