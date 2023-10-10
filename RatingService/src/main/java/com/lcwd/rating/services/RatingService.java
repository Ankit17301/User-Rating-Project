package com.lcwd.rating.services;

import com.lcwd.rating.entities.Rating;

import java.util.List;

public interface RatingService {
    //create
    Rating create(Rating rating);

    //get all ratings
    List<Rating> getRatings();

    //get all by userId
    List<Rating> getRatingsByUserId(String userId);

    //getall by hotelId
    List<Rating> getRatingsByHotelId(String hotelId);
}
