package com.bps.user.service.external.services;

import com.bps.user.service.entities.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="HOTEL-SERVICE")
public interface HotelService {
    @GetMapping("/hotels/{hotelId}")
    public Hotel getHotel(@PathVariable String hotelId);

}
