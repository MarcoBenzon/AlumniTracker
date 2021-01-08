package com.example.alumnitr;

public class EventsModel {
    String id, EventName, EventDate, EventContent;
    public EventsModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public String getEventContent() {
        return EventContent;
    }

    public void setEventContent(String eventContent) {
        EventContent = eventContent;
    }

    public EventsModel(String id, String EventName, String EventDate, String EventContent){
        this.id = id;
        this.EventName = EventName;
        this.EventDate = EventDate;
        this.EventContent = EventContent;


    }
}
