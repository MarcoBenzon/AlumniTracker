package com.example.alumnitr;

public class Model {

    String id, Subject, Sender, Content, Date;
    public Model(){}

    public Model(String id, String Subject, String Sender, String Content, String Date){
        this.id = id;
        this.Subject = Subject;
        this.Sender = Sender;
        this.Content = Content;
        this.Date = Date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
