package com.example.alumnitr;

public class JobsModel {

    String id, JobName, Status, JobsDate, Content;
    private JobsModel(){}

    public JobsModel(String id, String JobName, String Status, String JobsDate, String Content){
        this.id = id;
        this.JobName = JobName;
        this.Status = Status;
        this.JobsDate = JobsDate;
        this.Content = Content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobName() {
        return JobName;
    }

    public void setJobName(String jobName) {
        JobName = jobName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getJobsDate() {
        return JobsDate;
    }

    public void setJobsDate(String jobsDate) {
        JobsDate = jobsDate;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
