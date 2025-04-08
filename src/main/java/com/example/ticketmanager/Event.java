package com.example.ticketmanager;

public class Event {

    private String eventName;
    private String eventDescription;
    private String eventDate;
    private Boolean isVisible;

    public Event()
    {
        eventName = "Default Name";
        eventDescription = "Default Description";
        eventDate = "Default Date";
        isVisible = false;
    }




    public String getEventDate() {
        return eventDate;
    }
    public String getEventName() {
        return eventName;
    }
    public String getEventDescription() {
        return eventDescription;
    }
    public Boolean getIsVisible(){
        return isVisible;
    }

    public void setEventName(String newName) {
        if (!newName.isEmpty())
            eventName = newName;
    }
    public void setEventDescription(String newDescription) {
        if (!newDescription.isEmpty())
            eventDescription = newDescription;
    }
    public void setEventDate(String newDate) {
        if (!newDate.isEmpty())
            eventDate = newDate;
    }
    public void setIsVisible(Boolean newVisibility)
    {
        isVisible = newVisibility;
    }

    public String toString() {
        return getEventName() + " " + getEventDescription() + " " + getEventDate();
    }
}
