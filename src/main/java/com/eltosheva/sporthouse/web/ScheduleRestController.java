package com.eltosheva.sporthouse.web;

import com.eltosheva.sporthouse.models.service.ScheduleListServiceModel;
import com.eltosheva.sporthouse.services.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api")
public class ScheduleRestController {

    private final ScheduleService scheduleService;

    @RequestMapping(path = "/availableTrainings", method = RequestMethod.GET)
    public ResponseEntity<List<ScheduleListServiceModel>> findAll() {
        return ResponseEntity.ok().body(scheduleService.findAllAvailableTrainings(false));
    }
}