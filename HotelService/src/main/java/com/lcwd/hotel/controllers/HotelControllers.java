package com.lcwd.hotel.controllers;

import com.lcwd.hotel.entities.Hotel;
import com.lcwd.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelControllers {
    @Autowired
    private HotelService hotelService;
    //create
    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public ResponseEntity<Hotel> create(@RequestBody Hotel hotel) {
        return new ResponseEntity<>(hotelService.create(hotel), HttpStatus.CREATED);
    }

    //get single
    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String hotelId) {
        return new ResponseEntity<>(hotelService.get(hotelId), HttpStatus.OK);
    }

    //get all
    @PreAuthorize("hasAuthority('SCOPE_internal')")
    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        return new ResponseEntity<>(hotelService.getAll(), HttpStatus.OK);
    }

}
