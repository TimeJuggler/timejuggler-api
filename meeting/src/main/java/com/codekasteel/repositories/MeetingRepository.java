package com.codekasteel.repositories;

import com.codekasteel.entities.Meeting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

public interface MeetingRepository extends CrudRepository<Meeting, Long> {

    @Query("select b from Meeting b " +
            "where b.fromDate between ?1 and ?2 and b.toDate between ?1 and ?2")
    List<Meeting> findByDatesBetween(@Temporal(TemporalType.TIMESTAMP) Date from, @Temporal(TemporalType.TIMESTAMP)
            Date to);


    @Query("SELECT DISTINCT m FROM Meeting m " +
            "JOIN m.attendees a " +
            "WHERE a = :attendee " +
            "AND " +
            "(m.fromDate >= :from_date AND m.fromDate < :to_date OR " +
            "m.toDate > :from_date AND m.toDate <= :to_date OR " +
            "m.fromDate < :from_date AND m.toDate > :to_date)")
    List<Meeting> findMeetingByDatesBetween(
            @Temporal(TemporalType.TIMESTAMP) @Param("from_date") Date from,
                                     @Temporal(TemporalType.TIMESTAMP) @Param("to_date") Date to,
                                     @Param("attendee") String attendee);
}
