package edu.westfieldstate.eticketmanager.model;

public class Venue {
    private String venueName;
    private String venueLocation;

    public Venue()
    {
        setVenueName("DEFAULT VENUE");
        setVenueLocation("DEFAULT LOCATION");
    }

    public Venue(String newName, String newLocation)
    {
        setVenueName(newName);
        setVenueLocation(newLocation);
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        if (!venueName.isEmpty())
            this.venueName = venueName;
    }

    public String getVenueLocation() {
        return venueLocation;
    }

    public void setVenueLocation(String venueLocation) {
        if(!venueLocation.isEmpty())
            this.venueLocation = venueLocation;
    }
    @Override
    public String toString() {
        return venueName; // Only shows venue name in ComboBox
    }
}
