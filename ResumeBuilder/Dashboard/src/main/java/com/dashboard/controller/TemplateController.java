package com.dashboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.entity.Template;
import com.dashboard.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/api/template")
public class TemplateController {

    private final EventService eventService;

    public TemplateController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/api/templates")
    public List<Template> getTemplates(@RequestParam String eventName) {
        return eventService.getTemplatesForEvent(eventName);
    }
}
