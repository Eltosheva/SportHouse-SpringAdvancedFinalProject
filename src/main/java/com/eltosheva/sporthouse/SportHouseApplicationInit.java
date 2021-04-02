package com.eltosheva.sporthouse;

import com.eltosheva.sporthouse.services.PlaceService;
import com.eltosheva.sporthouse.services.SportService;
import com.eltosheva.sporthouse.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SportHouseApplicationInit implements CommandLineRunner {

    private final UserService userService;
    private final SportService sportService;
    private final PlaceService placeService;

    @Override
    public void run(String... args) throws Exception {
        userService.initUsers();
        sportService.initSports();
        placeService.initPlaces();
    }
}
