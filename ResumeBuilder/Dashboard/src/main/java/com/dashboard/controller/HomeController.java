package com.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dashboard.entity.Review;
import com.dashboard.entity.Template;
import com.dashboard.service.EventService;
import com.dashboard.service.ReviewService;

import java.util.List;

@Controller
public class HomeController {

    private final EventService eventService;
    
    @Autowired
    private ReviewService reviewService;

    public HomeController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        String currentEventName = getCurrentEventName();
        List<Template> templates = eventService.getTemplatesForEvent(currentEventName);
        model.addAttribute("templates", templates);
        model.addAttribute("eventName", currentEventName);
        return "index";
    }

    private String getCurrentEventName() {
        // Logic to determine the current event name, e.g., "Engineer's Day"
        // This can be replaced with a more dynamic approach as needed
        return "Engineer's Day";
    }
    
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewService.saveReview(review);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review reviewDetails) {
        return reviewService.getReviewById(id)
                .map(review -> {
                    review.setReviewerName(reviewDetails.getReviewerName());
                    review.setComments(reviewDetails.getComments());
                    review.setRating(reviewDetails.getRating());
                    Review updatedReview = reviewService.saveReview(review);
                    return ResponseEntity.ok(updatedReview);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        return reviewService.getReviewById(id)
                .map(review -> {
                    reviewService.deleteReview(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

