package com.codekasteel;

import com.codekasteel.entities.Meeting;
import com.codekasteel.repositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;

@SpringBootApplication
@EnableEurekaClient
public class MeetingApplication {

	@Autowired
	private MeetingRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(MeetingApplication.class, args);
	}

	@PostConstruct
	public void addDummyData() throws ParseException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");


		Meeting meeting = new Meeting();
		meeting.setId(12121L);
		meeting.setAttendees(Collections.singletonList("898"));
		meeting.setFromDate(dateFormatter.parse("20-03-2017 10:00"));
		meeting.setToDate(dateFormatter.parse("20-03-2017 11:00"));

		repository.save(meeting);

		meeting = new Meeting();
		meeting.setId(12121L);
		meeting.setAttendees(Collections.singletonList("898"));
		meeting.setFromDate(dateFormatter.parse("20-03-2017 12:00"));
		meeting.setToDate(dateFormatter.parse("20-03-2017 12:30"));

		repository.save(meeting);

		meeting = new Meeting();
		meeting.setId(12121L);
		meeting.setAttendees(Collections.singletonList("898"));
		meeting.setFromDate(dateFormatter.parse("20-03-2017 12:30"));
		meeting.setToDate(dateFormatter.parse("20-03-2017 14:00"));

		repository.save(meeting);
	}

}
