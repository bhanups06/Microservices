package com.bps.user.service.services.impl;

import com.bps.user.service.entities.Hotel;
import com.bps.user.service.entities.Rating;
import com.bps.user.service.entities.User;
import com.bps.user.service.exceptions.ResourceNotFoundException;
import com.bps.user.service.external.services.HotelService;
import com.bps.user.service.repositories.UserRepository;
import com.bps.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow( () -> new ResourceNotFoundException("User with given id is not present :"+userId));
        //fetch ratings of the user from RATING service
        //1e226fa3-7b06-4fc0-aaf6-5061a0dc3239http://localhost:8083/rating/users/2c1acca5-6c09-4674-aeb7-b26dae526f0f
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/rating/users/"+userId, Rating[].class);
        logger.info("{}",ratingsOfUser);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
        List<Rating> ratingList= ratings.stream().map(rating -> {
            //ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);

           //calling hotel service interface using Feign client
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
          //  logger.info("response status code : {}",forEntity.getStatusCode());
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());


        user.setRatings(ratingList);
        return user;
    }
}
