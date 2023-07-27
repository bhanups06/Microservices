package com.bps.user.service.external.services;

import com.bps.user.service.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="RATING-SERVICE")
public interface RatingService {

    @PostMapping("/rating")
    public ResponseEntity<Rating> createRating(Rating values);


    @PutMapping("/rating/{ratingId}")
    public ResponseEntity<Rating> updateRating(@PathVariable String ratingId, Rating values);


    @DeleteMapping("/rating/{ratingId}")
    public void deleteRating(@PathVariable String ratingId);
}
