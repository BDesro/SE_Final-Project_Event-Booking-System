import java.time.LocalDate;

public class Event {

    private String eventName;
    private String eventDescription;
    private LocalDate eventDate;
    private Boolean isVisible;

    public Event()
    {
        eventName = "Default Name";
        eventDescription = "Default Description";
        eventDate = LocalDate.now();
        isVisible = false;
    }




    public LocalDate getEventDate() {
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
    public void setEventDate(LocalDate newDate) {
        if (newDate != null)
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
