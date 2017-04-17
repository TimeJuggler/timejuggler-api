package com.codekasteel.controllers;

import com.codekasteel.commands.MeetingService;
import com.codekasteel.entities.Meeting;
import com.codekasteel.repositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    private static final String DD_MM_YYYY = "dd-MM-yyyy";
    @Autowired
    MeetingRepository repository;
    @Autowired
    private MeetingService meetingService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Meeting> createNewMeeting(@RequestBody Meeting meeting) {

        meetingService.adjustWholeDayEventToDate(meeting);

        if (meetingService.isNotOverlapping(meeting)) {

            Meeting created = repository.save(meeting);

            return new ResponseEntity<>(created, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Meeting>> getMeetingsBetweenDates(@RequestParam(name = "from") String from, @RequestParam(name = "to") String to, @RequestParam(name = "attendee") String attendee)
            throws ParseException {

        DateFormat df = new SimpleDateFormat(DD_MM_YYYY);
        List<Meeting> results = repository.findMeetingByDatesBetween(df.parse(from), df.parse(to), attendee);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @RequestMapping(value = "/{meetingId}", method = RequestMethod.GET)
    public ResponseEntity<Meeting> getMeetingById(@PathVariable Long meetingId) {

        Meeting result = repository.findOne(meetingId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{meetingId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteMeeting(@PathVariable Long meetingId) {

        repository.delete(meetingId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
