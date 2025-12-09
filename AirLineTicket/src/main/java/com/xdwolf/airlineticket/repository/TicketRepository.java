package com.xdwolf.airlineticket.repository;

import com.xdwolf.airlineticket.entity.TicketEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    @Query(value = "SELECT DISTINCT t FROM TicketEntity t LEFT JOIN FETCH t.passenger",
            countQuery = "SELECT COUNT(t) FROM TicketEntity t")
    Page<TicketEntity> findAll(Pageable pageable);

    long countByStatus(int status);

    @Query("SELECT COUNT(t) FROM TicketEntity t WHERE t.flight.id = :flightId AND t.status = 1")
    long countActiveTickets(@Param("flightId") Long flightId);

    @Query("SELECT COALESCE(SUM(t.totalPrice), 0) FROM TicketEntity t " +
            "WHERE MONTH(t.createdDate) = :month AND YEAR(t.createdDate) = :year AND t.status = 1")
    Long getPriceByMonth(@Param("month") int month, @Param("year") int year);
}
