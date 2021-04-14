package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Schedule;
import com.eltosheva.sporthouse.models.service.ScheduleServiceModel;
import com.eltosheva.sporthouse.repositories.ScheduleRepository;
import com.eltosheva.sporthouse.services.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ModelMapper modelMapper) {
        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addRecord(ScheduleServiceModel scheduleServiceModel) {
        Schedule schedule = modelMapper.map(scheduleServiceModel, Schedule.class);
        scheduleRepository.saveAndFlush(schedule);
    }

    @Override
    public List<ScheduleServiceModel> findAllByUsers_email() {
        return scheduleRepository
                .findAllByUsers_Email(SecurityContextHolder.getContext().getAuthentication().getName())
                .stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleServiceModel.class))
                .collect(Collectors.toList());
    }
}
