package com.codekasteel.commands;

import com.codekasteel.entities.Meeting;
import com.codekasteel.repositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MeetingService {

    public static final Calendar CALENDAR = Calendar.getInstance();
    @Autowired
    MeetingRepository repository;

    /**
     * Checks by retrieving all the meetings who's start and end
     * times overlap with the meeting's times. If the result is a non empty
     * list that means that there is an overlapping meeting and the result is {@code false}.
     *
     * @param meeting the meeting to check for
     * @return {@code true} if there is an overlapping meeting, {@code false} if otherwise.
     */
    public boolean isNotOverlapping(Meeting meeting) {

        List<Meeting> result = repository.findMeetingByDatesBetween(
        meeting.getFromDate(), meeting.getToDate(),
                meeting.getAttendees().get(0));

        return result.isEmpty();
    }

    public void adjustWholeDayEventToDate(@RequestBody Meeting meeting) {

        if (meeting.isWholeDayEvent()) {
            meeting.setToDate(increaseToStartOfNextDay(meeting.getFromDate()));
        }
    }

    private Date increaseToStartOfNextDay(Date fromDate) {

        CALENDAR.setTime(fromDate);
        CALENDAR.add(Calendar.DATE, 1);
        CALENDAR.set(Calendar.HOUR_OF_DAY, 0);
        return CALENDAR.getTime();
    }
}
