package com.eltosheva.sporthouse.models.service;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ScheduleListServiceModel {

    private String date;
    private List<ScheduleServiceModel> scheduleList;
}
