package com.dashboard.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.dashboard.dao.EventRepository;
import com.dashboard.dao.TemplateRepository;
import com.dashboard.entity.Event;
import com.dashboard.entity.Template;

import java.io.IOException;
import java.util.List;

@Service
public class EventService {

    private final CalendarService calendarService;
    private final EventRepository eventRepository;
    private final TemplateRepository templateRepository;

    public EventService(CalendarService calendarService, EventRepository eventRepository, TemplateRepository templateRepository) {
        this.calendarService = calendarService;
        this.eventRepository = eventRepository;
        this.templateRepository = templateRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Runs every day at midnight
    public void fetchAndSaveEvents() throws IOException {
        List<com.google.api.services.calendar.model.Event> events = calendarService.getUpcomingEvents();
        for (com.google.api.services.calendar.model.Event event : events) {
            Event newEvent = new Event();
            newEvent.setName(event.getSummary());
            newEvent.setDate(event.getStart().getDateTime().toString());
            newEvent.setTags(event.getDescription()); // Assuming tags are in the description
            eventRepository.save(newEvent);
        }
    }

    public List<Template> getTemplatesForEvent(String eventName) {
        Event event = eventRepository.findByName(eventName);
        return templateRepository.findByEventTagsContaining(event.getTags());
    }
}

