package com.hotelmanagement.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelmanagement.entity.Room;
import com.hotelmanagement.repository.RoomRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class RoomSyncService {

    @Autowired
    private RoomRepository roomRepository;

    @PostConstruct
    public void syncRoomsFromLocalJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass().getResourceAsStream("/data/rooms.json");

            if (inputStream == null) {
                throw new IllegalStateException("❌ rooms.json file not found in /resources/data/");
            }

            List<Room> rooms = mapper.readValue(inputStream, new TypeReference<List<Room>>() {});
            for (Room room : rooms) {
                if (!roomRepository.existsByRoomNumber(room.getRoomNumber())) {
                    roomRepository.save(room);
                }
            }

            System.out.println("✅ " + rooms.size() + " rooms imported from rooms.json.");
        } catch (Exception e) {
            System.out.println("❌ Failed to import room data: " + e.getMessage());
        }
    }
}
