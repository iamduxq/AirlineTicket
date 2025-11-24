package com.xdwolf.airlineticket.component;

import com.xdwolf.airlineticket.converter.*;
import com.xdwolf.airlineticket.repository.*;
import org.springframework.stereotype.Component;

@Component
public class ServiceHelper {
    public final AirportRepository airportRepository;
    public final UserRepository userRepository;
    public final FlightRepository flightRepository;
    public final BookingRepository bookingRepository;
    public final TicketRepository ticketRepository;
    public final PassengerRepository passengerRepository;
    public final AirportConverter airportConverter;
    public final UserConverter userConverter;
    public final FlightConverter flightConverter;
    public final BookingConverter bookingConverter;
    public final TicketConverter ticketConverter;
    public final PassengerConverter passengerConverter;


    public ServiceHelper(AirportRepository airportRepository, UserRepository userRepository, FlightRepository flightRepository, BookingRepository bookingRepository, TicketRepository ticketRepository, PassengerRepository passengerRepository, AirportConverter airportConverter, UserConverter userConverter, FlightConverter flightConverter, BookingConverter bookingConverter, TicketConverter ticketConverter, PassengerConverter passengerConverter) {
        this.airportRepository = airportRepository;
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
        this.ticketRepository = ticketRepository;
        this.passengerRepository = passengerRepository;
        this.airportConverter = airportConverter;
        this.userConverter = userConverter;
        this.flightConverter = flightConverter;
        this.bookingConverter = bookingConverter;
        this.ticketConverter = ticketConverter;
        this.passengerConverter = passengerConverter;
    }
}
