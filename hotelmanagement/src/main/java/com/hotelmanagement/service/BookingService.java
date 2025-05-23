package com.hotelmanagement.service;

import com.hotelmanagement.entity.Booking;
import com.hotelmanagement.entity.Room;
import com.hotelmanagement.entity.User;
import com.hotelmanagement.repository.BookingRepository;
import com.hotelmanagement.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    public Booking bookRoom(User user, Room room, String checkIn, String checkOut) {
        long days = ChronoUnit.DAYS.between(
                java.time.LocalDate.parse(checkIn),
                java.time.LocalDate.parse(checkOut)
        );
        double total = days * room.getPricePerNight();

        room.setIsAvailable(false);
        roomRepository.save(room);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setCheckInDate(java.time.LocalDate.parse(checkIn));
        booking.setCheckOutDate(java.time.LocalDate.parse(checkOut));
        booking.setTotalPrice(total);

        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public void cancelBooking(Long id) {
        Optional<Booking> bookingOpt = bookingRepository.findById(id);
        bookingOpt.ifPresent(booking -> {
            Room room = booking.getRoom();
            room.setIsAvailable(true);
            roomRepository.save(room);
            bookingRepository.deleteById(id);
        });
    }
}
