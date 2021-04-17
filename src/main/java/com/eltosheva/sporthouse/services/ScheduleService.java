package com.eltosheva.sporthouse.services;

import com.eltosheva.sporthouse.models.service.ScheduleListServiceModel;
import com.eltosheva.sporthouse.models.service.ScheduleServiceModel;

import java.util.List;

public interface ScheduleService {
    void addRecord(ScheduleServiceModel scheduleServiceModel);
    List<ScheduleListServiceModel> findAllByUser_email();
    List<ScheduleListServiceModel> findAllAvailableTrainings(boolean shouldFilterEmails);
}
