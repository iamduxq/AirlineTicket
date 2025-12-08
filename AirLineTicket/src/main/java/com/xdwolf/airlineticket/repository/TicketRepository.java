package com.xdwolf.airlineticket.repository;

import com.xdwolf.airlineticket.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    long countByStatus(int status);
//    @Query("SELECT SUM(t.totalPrice) From TicketEntity t " +
//            "where MONTH(t.createdDate) =:month AND YEAR(t.createdDate) =:year AND t.status = 2")
//    Long getPriceByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT COUNT(t) FROM TicketEntity t WHERE t.flight.id = :flightId AND t.status = 1")
    long countActiveTickets(@Param("flightId") Long flightId);

    @Query("SELECT COALESCE(SUM(t.totalPrice), 0) FROM TicketEntity t " +
            "WHERE MONTH(t.createdDate) = :month AND YEAR(t.createdDate) = :year AND t.status = 1")
    Long getPriceByMonth(@Param("month") int month, @Param("year") int year);
}
