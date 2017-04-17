package com.codekasteel.entities;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ElementCollection
    @Column(nullable = false)
    private List<String> attendees;

    @ElementCollection
    private List<String> assets;

    @Column(nullable = false)
    private Date fromDate;

    @Column(nullable = false)
    private Date toDate;

    @Column(nullable = false)
    private boolean isWholeDayEvent = false;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availability = AvailabilityStatus.BUSY;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<String> attendees) {
        this.attendees = attendees;
    }

    public List<String> getAssets() {
        return assets;
    }

    public void setAssets(List<String> assets) {
        this.assets = assets;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public boolean isWholeDayEvent() {

        return isWholeDayEvent;
    }

    public void setWholeDayEvent(boolean wholeDayEvent) {

        isWholeDayEvent = wholeDayEvent;
    }

    @Override
    public String toString() {

        return "Meeting{" + "id=" + id + ", attendees=" + attendees + ", assets=" + assets + ", fromDate=" + fromDate + ", toDate=" + toDate + '}';
    }

    public AvailabilityStatus getAvailability() {

        return availability;
    }

    public void setAvailability(AvailabilityStatus availability) {

        this.availability = availability;
    }
}
