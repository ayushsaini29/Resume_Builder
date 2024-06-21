package com.dashboard.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.util.List;

@Service
public class CalendarService {

    @Value("${google.calendar.id}")
    private String calendarId;

    private final Calendar calendar;

    public CalendarService(Calendar calendar) {
        this.calendar = calendar;
    }

    public List<Event> getUpcomingEvents() throws IOException {
        Events events = calendar.events().list(calendarId)
                .setMaxResults(10)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .setTimeMin(new DateTime(System.currentTimeMillis()))
                .execute();
        return events.getItems();
       
    }
}

