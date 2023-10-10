package com.lcwd.user.service.services.impl;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.external.services.HotelService;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;
    //create
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    //get all users
    @Override
    public List<User> getAllUsers() {
        //implementing RATING SERVICE CALL: USING REST TEMPLATE
        return userRepository.findAll();
    }

    //get single user
    @Override
    public User getUser(String userId) {
        //get user from database with the help of userRepository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Resource not found on server"));
        //fetch rating of the above user from RATING_SERVICE
        //http://localhost:8083/rating/user/7367c434-83ce-42b1-92a1-37e92339ca7b
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/rating/user/" + userId, Rating[].class);
        List<Rating> ratings = Arrays.asList(ratingsOfUser);
        logger.info("{}", ratings);

        List<Rating> ratingList = ratings.stream().map(rating -> {
            //api call to hotel service to get the hotel object
            //http://localhost:8082/hotel/293640f6-fd8d-4ec1-a32c-c631b76dd1b6
//                ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotel/"+ rating.getHotelId(), Hotel.class);
//                Hotel hotel = forEntity.getBody();
//                logger.info("response status code: {}",forEntity.getStatusCode());

            //Using Feign Client
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
            //set the hotel object to rating
            rating.setHotel(hotel);
            //return  rating
            return rating;
        }).collect(Collectors.toList());
        user.setRatings(ratingList);
        return user;
    }
}
