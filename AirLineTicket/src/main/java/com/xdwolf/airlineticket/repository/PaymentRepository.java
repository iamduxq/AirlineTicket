package com.xdwolf.airlineticket.repository;

import com.xdwolf.airlineticket.dto.PaymentDTO;
import com.xdwolf.airlineticket.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
