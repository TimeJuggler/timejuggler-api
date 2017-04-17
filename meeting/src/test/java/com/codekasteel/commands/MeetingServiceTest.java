package com.codekasteel.commands;

import com.codekasteel.entities.Meeting;
import com.codekasteel.utilities.TestingUtilities;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MeetingServiceTest {

    private MeetingService meetingService = new MeetingService();

    @Test
    public void adjustWholeDayEventToDate() throws Exception {

        Date date = TestingUtilities.DATE_FORMATTER.parse("20-03-2018 08:00");

        Meeting meeting = new Meeting();
        meeting.setFromDate(date);
        meeting.setWholeDayEvent(true);

        meetingService.adjustWholeDayEventToDate(meeting);

        assertThat(TestingUtilities.DATE_FORMATTER.format(meeting.getToDate()), is("21-03-2018 00:00"));
    }


}