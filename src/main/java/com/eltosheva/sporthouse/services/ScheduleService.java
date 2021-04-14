package com.eltosheva.sporthouse.services;

import com.eltosheva.sporthouse.models.service.ScheduleServiceModel;

import java.util.List;

public interface ScheduleService {
    void addRecord(ScheduleServiceModel scheduleServiceModel);
    List<ScheduleServiceModel> findAllByUsers_email();
}
