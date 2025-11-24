package com.xdwolf.airlineticket.service;

import com.xdwolf.airlineticket.dto.PassengerDTO;

import java.util.List;

public interface IPassengerService {
    PassengerDTO save(PassengerDTO dto);
    List<PassengerDTO> saveAll(List<PassengerDTO> passengers);
    List<PassengerDTO> findByTicketId(Long ticketId);

}
