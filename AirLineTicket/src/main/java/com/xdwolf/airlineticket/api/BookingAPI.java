package com.xdwolf.airlineticket.api;


import com.xdwolf.airlineticket.dto.BookingDTO;
import com.xdwolf.airlineticket.dto.PassengerDTO;
import com.xdwolf.airlineticket.dto.requestDTO.BookingRequestDTO;
import com.xdwolf.airlineticket.service.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8888")
@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingAPI {
    private final IBookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(
            @RequestParam Long flightId,
            @RequestParam String username) {
        try {
            BookingDTO result = bookingService.createBooking(flightId, username);
            return ResponseEntity.ok(result);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Quản lý vé theo username
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserBookings(@PathVariable String username) {
        return ResponseEntity.ok(bookingService.findByUsername(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingDetails(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookingService.getBookingDetails(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Đặt vé
    @PostMapping("/book-ticket")
    public BookingDTO bookTicket(@RequestBody BookingRequestDTO dto) {
        return bookingService.bookTicket(dto);
    }

    // Cập nhật vé
    @PutMapping("/{bookingId}/update-passenger")
    public ResponseEntity<?> updatePassenger(@PathVariable Long bookingId,
                                             @RequestBody PassengerDTO passengerDTO) {
        try {
            return ResponseEntity.ok(bookingService.updatePassenger(bookingId, passengerDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Hủy vé
    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookingService.cancelBooking(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Xuất ra pdf
    @GetMapping("/{bookingId}/ticket-pdf")
    public ResponseEntity<byte[]>exportTicketPdf(@PathVariable Long bookingId) {
        byte[] pdfByte = null;
        try {
            pdfByte = bookingService.exportTicketPdf(bookingId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "ticket_" + bookingId + ".pdf");
        return new ResponseEntity<>(pdfByte, headers, HttpStatus.OK);
    }
}
