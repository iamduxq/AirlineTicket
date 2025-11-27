package com.xdwolf.airlineticket.service.impl;

import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.xdwolf.airlineticket.component.ServiceHelper;
import com.xdwolf.airlineticket.dto.BookingDTO;
import com.xdwolf.airlineticket.dto.PassengerDTO;
import com.xdwolf.airlineticket.dto.requestDTO.BookingRequestDTO;
import com.xdwolf.airlineticket.entity.*;
import com.xdwolf.airlineticket.service.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.StyleConstants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {

    private final ServiceHelper serviceHelper;

    @Override
    public BookingDTO createBooking(Long flightId, String username) {
        UserEntity user = serviceHelper.userRepository.findFirstByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        FlightEntity flight = serviceHelper.flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến bay!"));

        if (flight.getAvailableSeat() <= 0) {
            throw new RuntimeException("Hết ghế trống cho chuyến bay này!");
        }

        flight.setAvailableSeat(flight.getAvailableSeat() - 1);
        serviceHelper.flightRepository.save(flight);

        BookingEntity booking = new BookingEntity();
        booking.setUser(user);
//        booking.setFlight(flight);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("CONFIRMED");
        serviceHelper.bookingRepository.save(booking);
        return serviceHelper.bookingConverter.toDTO(booking);
    }

    @Override
    public List<BookingDTO> findByUsername(String username) {
        List<BookingEntity> bookings = serviceHelper.bookingRepository.findByUser_Username(username);
        return serviceHelper.bookingConverter.toDTOList(bookings);
    }

    @Override
    @Transactional
    public BookingDTO bookTicket(BookingRequestDTO request) {
        UserEntity user = serviceHelper.userRepository
                .findFirstByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        FlightEntity flight = serviceHelper.flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến bay!"));

        if (flight.getAvailableSeat() <= 0) {
            throw new RuntimeException("Hiện giờ chuyến bay đã hết ghế trống");
        }
        flight.setAvailableSeat(flight.getAvailableSeat() - 1);
        serviceHelper.flightRepository.save(flight);

        TicketEntity ticket = new TicketEntity();
        ticket.setUser(user);
        ticket.setFlight(flight);
        ticket.setSeatNumber(request.getSeatNumber());
        ticket.setTotalPrice(request.getTotalPrice());
        ticket.setStatus((byte) 1);
        ticket.setBookingDate(LocalDateTime.now());
        ticket = serviceHelper.ticketRepository.save(ticket);

        for (PassengerDTO p : request.getPassengers()) {
            PassengerEntity pe = serviceHelper.passengerConverter.toEntity(p);
            pe.setTicket(ticket);
            serviceHelper.passengerRepository.save(pe);
        }

        BookingEntity booking = new BookingEntity();
        booking.setTicket(ticket);
        booking.setUser(user);
        booking.setStatus("CONFIRMED");
        booking.setBookingDate(LocalDateTime.now());
        ticket.setBooking(booking);
        booking = serviceHelper.bookingRepository.save(booking);
        return serviceHelper.bookingConverter.toDTO(booking);
    }


    @Override
    @Transactional
    public BookingDTO updatePassenger(Long bookingId, PassengerDTO newPassenger) {
        BookingEntity booking = serviceHelper.bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé đặt"));

//        if (!booking.getStatus().equals("CONFIRMED")) {
//            throw new RuntimeException("Chỉ có thể sửa thông tin khi vé ở trạng thái xác nhận!");
//        }

        TicketEntity ticket = booking.getTicket();
        if (ticket == null) {
            throw new RuntimeException("Booking không có thông tin vé!");
        }

        PassengerEntity passenger = serviceHelper.passengerRepository.findFirstByTicket(ticket);
        passenger.setFullname(newPassenger.getFullname());
        passenger.setGender(newPassenger.getGender());
        passenger.setPassportNumber(newPassenger.getPassportNumber());
        passenger.setNationality(newPassenger.getNationality());
        passenger.setDateOfBirth(newPassenger.getDayOfBirth().toLocalDate());
        serviceHelper.passengerRepository.save(passenger);
        return serviceHelper.bookingConverter.toDTO(booking);
    }

    @Override
    @Transactional
    public BookingDTO cancelBooking(Long bookingId) {
        BookingEntity booking = serviceHelper.bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé đã đật"));

        TicketEntity ticket = booking.getTicket();
        if (ticket == null) {
            throw new RuntimeException("Vé không tồn tại");
        }

        FlightEntity flight = ticket.getFlight();
        if (flight != null) {
            flight.setAvailableSeat(flight.getAvailableSeat() + 1);
            serviceHelper.flightRepository.save(flight);
        }

        booking.setStatus("CANCELLED");
        ticket.setStatus((byte) 0);
        serviceHelper.ticketRepository.save(ticket);
        serviceHelper.bookingRepository.save(booking);
        return serviceHelper.bookingConverter.toDTO(booking);
    }

    @Override
    public BookingDTO getBookingDetails(Long bookingId) {
        BookingEntity booking = serviceHelper.bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé đã đặt"));
        return serviceHelper.bookingConverter.toDTO(booking);
    }

    @Override
    public byte[] exportTicketPdf(Long bookingId) throws IOException {
        BookingEntity booking = serviceHelper.bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        TicketEntity ticket = booking.getTicket();
        FlightEntity flight = ticket.getFlight();
        PassengerEntity passenger = ticket.getPassenger().get(0);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);

        float width = 420;
        float height = 530;
        pdf.setDefaultPageSize(new PageSize(width, height));
        Document document = new Document(pdf);
        PdfFont font = PdfFontFactory.createFont("./static/fonts/Roboto-Regular.ttf", PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
        PdfFont fontBold = PdfFontFactory.createFont("./static/fonts/Roboto-Bold.ttf", PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
        document.setFont(font);
        Paragraph title = new Paragraph("VÉ MÁY BAY ĐIỆN TỬ")
                .setFontSize(20)
                .setFont(fontBold)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(title);
        String qrText = "https://localhost:8888/booking-management?id=" + bookingId;
        BarcodeQRCode qr = new BarcodeQRCode(qrText);
        PdfFormXObject qrObject = qr.createFormXObject(ColorConstants.BLACK, pdf);
        Image qrImage = new Image(qrObject).setWidth(80).setHeight(80).setTextAlignment(TextAlignment.CENTER);
        document.add(qrImage);
        document.add(new Paragraph("Mã vé: " + ticket.getTicketCode()).setFontSize(14));
        document.add(new Paragraph("Họ và tên: " + passenger.getFullname()).setFontSize(14));
        document.add(new Paragraph("Giới tính: " + (passenger.getGender().equals("1") ? "Nam" : "Nữ")).setFontSize(14));
        document.add(new Paragraph("Ngày sinh: " + passenger.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).setFontSize(14));
        document.add(new Paragraph("CCCD / Passport: " + passenger.getPassportNumber()).setFontSize(14));
        document.add(new Paragraph("Quốc tịch: " + passenger.getNationality()).setFontSize(14));
        document.add(new Paragraph("Chuyến bay: " + flight.getAirline() + " " + flight.getFightCode()).setFontSize(14));
        document.add(new Paragraph("Giờ bay: " + flight.getDepartureTime().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"))).setFontSize(14));
        document.add(new Paragraph("Điểm bay: " + flight.getDepartureAirport().getCity()).setFontSize(14));
        document.add(new Paragraph("Điểm đến: " + flight.getArrivalAirport().getCity()).setFontSize(14));
        NumberFormat vndFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        document.add(new Paragraph("Giá tiền: " + vndFormat.format(flight.getPrice()) + " VND").setFontSize(14));
        document.close();
        return out.toByteArray();
    }

    @Override
    @Transactional
    public BookingDTO restoreBooking(Long bookingId) {
        BookingEntity booking = serviceHelper.bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking không tồn tại"));
        TicketEntity ticket = booking.getTicket();
        if (ticket == null) {
            throw new RuntimeException("Vé không tồn tại");
        }
        if (ticket.getStatus() != 0) {
            throw new RuntimeException("Không thể thu hồi vì vé không ở trạng thái hủy");
        }
        FlightEntity flight = ticket.getFlight();
        if (flight != null) {
            flight.setAvailableSeat(flight.getAvailableSeat() - 1);
            serviceHelper.flightRepository.save(flight);
        }
        ticket.setStatus((byte) 1);
        booking.setStatus("CONFIRMED");
        serviceHelper.ticketRepository.save(ticket);
        serviceHelper.bookingRepository.save(booking);
        return serviceHelper.bookingConverter.toDTO(booking);
    }
}
