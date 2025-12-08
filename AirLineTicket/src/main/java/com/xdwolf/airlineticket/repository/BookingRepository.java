package com.xdwolf.airlineticket.repository;

import com.xdwolf.airlineticket.entity.BookingEntity;
import com.xdwolf.airlineticket.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    @EntityGraph(attributePaths = {
            "ticket",
            "ticket.flight",
            "ticket.passenger",
            "ticket.flight.departureAirport",
            "ticket.flight.arrivalAirport"
    })
    List<BookingEntity> findByUser_Username(String username);

    @EntityGraph(attributePaths = {
            "ticket",
            "ticket.flight",
            "ticket.passenger",
            "ticket.flight.departureAirport",
            "ticket.flight.arrivalAirport"
    })
    Optional<BookingEntity> findById(Long id);

    @Query("""
    SELECT b FROM BookingEntity b
    LEFT JOIN FETCH b.ticket t
    LEFT JOIN FETCH t.flight f
    LEFT JOIN FETCH t.passenger p
    ORDER BY b.bookingDate DESC
    """)
    Page<BookingEntity> findAll(Pageable pageable);
}
