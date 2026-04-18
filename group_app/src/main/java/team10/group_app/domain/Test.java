package team10.group_app.domain;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import team10.group_app.dto.TestDTO;

/*
 * Test entity representing a test assessment in the system.
 * id, duration, startTime, ongoing, autograded
 * Extends Assessment
 */
@Entity
//@Table(name = "test")

public class Test extends Assessment {


    @Column(nullable = false)
    private LocalDateTime duration;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private Boolean ongoing;

    @Column(nullable = false)
    private Boolean autograded;


    public Test() { // Spring Data JPA requires a no arg constructor
    }

    public LocalDateTime getDuration() {
        return duration;
    }

    public void setDuration(LocalDateTime duration){
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime){
        this.startTime = startTime;
    }

    public Boolean getAutograded() {
        return autograded;
    }
    public void setAutograded(Boolean autograded){
        this.autograded = autograded;
    }

    public Boolean getOngoing() {
        return ongoing;
    }

    public void setOngoing(Boolean ongoing){
        this.ongoing = ongoing;
    }

    public TestDTO toDTO() {
        TestDTO dto = new TestDTO();
        dto.setId(this.getId());
        dto.setSetter(this.getSetter());
        dto.setChecker(this.getChecker());
        dto.setName(this.getName());
        dto.setReleasedToStudents(this.getReleasedToStudents());
        dto.setDuration(this.duration);
        dto.setStartTime(this.startTime);
        dto.setOngoing(this.ongoing);
        dto.setAutograded(this.autograded);
        return dto;
    }

}