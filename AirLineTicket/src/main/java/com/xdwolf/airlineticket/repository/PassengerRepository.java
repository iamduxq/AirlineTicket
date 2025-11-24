package com.xdwolf.airlineticket.repository;

import com.xdwolf.airlineticket.entity.PassengerEntity;
import com.xdwolf.airlineticket.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<PassengerEntity, Long> {
    List<PassengerEntity> findByTicket(TicketEntity ticket);
    PassengerEntity findFirstByTicket(TicketEntity ticket);
}
