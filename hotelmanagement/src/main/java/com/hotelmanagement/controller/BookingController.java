package com.hotelmanagement.controller;

import com.hotelmanagement.entity.Booking;
import com.hotelmanagement.entity.Room;
import com.hotelmanagement.entity.User;
import com.hotelmanagement.service.BookingService;
import com.hotelmanagement.service.RoomService;
import com.hotelmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Booking bookRoom(@RequestParam String username,
                            @RequestParam Long roomId,
                            @RequestParam String checkIn,
                            @RequestParam String checkOut) {

        Optional<User> user = userService.getUserByUsername(username);
        Optional<Room> room = roomService.getRoomById(roomId);

        if (user.isPresent() && room.isPresent()) {
            return bookingService.bookRoom(user.get(), room.get(), checkIn, checkOut);
        }
        throw new RuntimeException("Invalid user or room");
    }

    @GetMapping("/user/{username}")
    public List<Booking> getUserBookings(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return user.map(bookingService::getBookingsByUser)
                   .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @DeleteMapping("/{id}")
    public void cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
    }
}
