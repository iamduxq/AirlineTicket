package com.xdwolf.airlineticket.service.admin;

import com.xdwolf.airlineticket.entity.FlightEntity;
import com.xdwolf.airlineticket.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightStatusService {

    private final FlightRepository flightRepository;

    @Transactional
    public void updateFlightStatus() { // Cập nhật status chuyến bay
        LocalDateTime today = LocalDateTime.now();
        List<FlightEntity> flights = flightRepository.findAll();
        for (FlightEntity flight : flights) {
            LocalDateTime departureTime = flight.getDepartureTime();
            LocalDateTime arrivalTime = flight.getArrivalTime();
            boolean complete =false;

            if (arrivalTime != null) {
                if (arrivalTime.isBefore(today)) {
                    complete = true;
                }
            }
            else {
                if (departureTime.isBefore(today)) {
                    complete = true;
                }
            }

            if (complete && flight.getStatus() != 2) {
                flight.setStatus((byte) 2);
            }
        }
        flightRepository.saveAll(flights);
    }
}
