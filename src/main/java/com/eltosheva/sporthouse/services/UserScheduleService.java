package com.eltosheva.sporthouse.services;

import com.eltosheva.sporthouse.models.service.ScheduleListServiceModel;

import java.util.List;

public interface UserScheduleService {
     List<ScheduleListServiceModel> findAllUserSchedules();
}
