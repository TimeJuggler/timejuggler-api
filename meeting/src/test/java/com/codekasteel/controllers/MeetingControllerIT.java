package com.codekasteel.controllers;

import com.codekasteel.entities.Meeting;
import com.codekasteel.utilities.TestingUtilities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class MeetingControllerIT {

    @Autowired
    MockMvc mockMvc;
    private List<Meeting> meetings = new ArrayList<>();

    public static String asJsonString(final Object obj) throws JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(obj);

        System.out.println(jsonContent);

        return jsonContent;
    }

    @Before
    public void setUp() throws Exception {

        Meeting meeting = new Meeting();
        meeting.setAttendees(Collections.singletonList("898"));
        meeting.setFromDate(TestingUtilities.DATE_FORMATTER.parse("20-03-2018 10:00"));
        meeting.setToDate(TestingUtilities.DATE_FORMATTER.parse("20-03-2018 11:00"));

        meetings.add(saveMeeting(meeting, HttpStatus.CREATED));

        meeting = new Meeting();
        meeting.setAttendees(Collections.singletonList("898"));
        meeting.setFromDate(TestingUtilities.DATE_FORMATTER.parse("20-03-2018 12:00"));
        meeting.setToDate(TestingUtilities.DATE_FORMATTER.parse("20-03-2018 12:30"));

        meetings.add(saveMeeting(meeting, HttpStatus.CREATED));

        meeting = new Meeting();
        meeting.setAttendees(Collections.singletonList("898"));
        meeting.setFromDate(TestingUtilities.DATE_FORMATTER.parse("20-03-2018 12:30"));
        meeting.setToDate(TestingUtilities.DATE_FORMATTER.parse("20-03-2018 14:00"));

        meetings.add(saveMeeting(meeting, HttpStatus.CREATED));
    }

    private Meeting saveMeeting(Meeting meeting, HttpStatus httpStatus) throws Exception {

        MvcResult result = mockMvc.perform(post("/meeting")
                .content(asJsonString(meeting))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(httpStatus.value()))
                .andReturn();

        String response = result.getResponse().getContentAsString();

        return getMeetingFromJson(response);
    }

    private Meeting getMeetingFromJson(String response) throws java.io.IOException {
        Meeting created = null;

        if(!response.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            created = mapper.readValue(response, Meeting.class);
        }
        return created;
    }

    @Test
    public void shouldAddMeeting() throws Exception {
        Meeting meeting = new Meeting();
        meeting.setAttendees(Collections.singletonList("898"));
        meeting.setFromDate(TestingUtilities.DATE_FORMATTER.parse("20-03-2018 14:30"));
        meeting.setToDate(TestingUtilities.DATE_FORMATTER.parse("20-03-2018 15:00"));


        Meeting created = saveMeeting(meeting, HttpStatus.CREATED);
        assertThat(created.getId(), greaterThan(0L));
    }

    @Test
    public void shouldNotAddMeetingWhenTimeSlotIsNotAvailable() throws Exception {
        Meeting meeting = new Meeting();
        meeting.setAttendees(Collections.singletonList("898"));
        meeting.setFromDate(TestingUtilities.DATE_FORMATTER.parse("20-03-2018 10:30"));
        meeting.setToDate(TestingUtilities.DATE_FORMATTER.parse("20-03-2018 11:30"));


        Meeting created = saveMeeting(meeting, HttpStatus.CONFLICT);
        assertThat(created, is(nullValue()));
    }

    @Test
    public void shouldNotAddMeetingWhenTimeSlotIsSuperset() throws Exception {
        Meeting meeting = new Meeting();
        meeting.setAttendees(Collections.singletonList("898"));
        meeting.setFromDate(TestingUtilities.DATE_FORMATTER.parse("20-03-2018 08:00"));
        meeting.setToDate(TestingUtilities.DATE_FORMATTER.parse("20-03-2018 15:00"));


        Meeting created = saveMeeting(meeting, HttpStatus.CONFLICT);
        assertThat(created, is(nullValue()));
    }

    @After
    public void tearDown() throws Exception {

        for (Meeting meeting : meetings) {

            mockMvc.perform(delete("/meeting/" + meeting.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        }
    }



}