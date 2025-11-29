package com.xdwolf.airlineticket.repository;

import com.xdwolf.airlineticket.entity.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
    FlightEntity findFlightEntityById(Long id);
    boolean existsByFightCode(String code);
//    FlightEntity findById(Long id);

    @Query("select f from FlightEntity f " +
    "where f.departureAirport.code = :from " +
    "and f.arrivalAirport.code = :to " +
    "and f.departureTime between :startOfDay and :endOfDay")
    List<FlightEntity> findByAirportsAndDate(@Param("from") String from,
                                             @Param("to") String to,
                                             @Param("startOfDay") LocalDateTime startOfDay,
                                             @Param("endOfDay") LocalDateTime endOfDay
    );

    long countByStatus(int status);
    long count();
}
