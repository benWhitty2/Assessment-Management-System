package team10.group_app.dto;

public class ProgressStepDTO {
    private int id;
    private String title;
    private String status;
    private String description;
    private String stageName;

    public ProgressStepDTO(int id, String title, String status, String description, String stageName) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.description = description;
        this.stageName = stageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }
}
