package com.bps.rating.services;

import com.bps.rating.entities.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {

     Rating create(Rating rating);

     List<Rating> getRatings();

     List<Rating> getRatingByUserId(String userId);

     List<Rating> getRatingByHotelId(String hotelId);


}
