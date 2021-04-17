package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Place;
import com.eltosheva.sporthouse.models.entities.Schedule;
import com.eltosheva.sporthouse.models.entities.User;
import com.eltosheva.sporthouse.models.service.ScheduleListServiceModel;
import com.eltosheva.sporthouse.models.service.ScheduleServiceModel;
import com.eltosheva.sporthouse.repositories.*;
import com.eltosheva.sporthouse.services.ScheduleService;
import com.eltosheva.sporthouse.utils.DateHelper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final PlaceRepository placeRepository;
    private final ModelMapper modelMapper;
    private final UserScheduleRepository userScheduleRepository;

    @Transactional
    @Override
    public void addRecord(ScheduleServiceModel scheduleServiceModel) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(IllegalArgumentException::new);
        Place place = placeRepository.findById(scheduleServiceModel.getPlaceId()).orElseThrow(IllegalArgumentException::new);

        Schedule schedule = modelMapper.map(scheduleServiceModel, Schedule.class);
        schedule.setIsActive(true);
        schedule.setPlace(place);
        schedule.setId(null);
        schedule.setUser(user);
        scheduleRepository.saveAndFlush(schedule);
    }

    @Override
    public List<ScheduleListServiceModel> findAllByUser_email() {
        return findAllAvailableTrainings(true);
    }

    @Transactional
    @Override
    public List<ScheduleListServiceModel> findAllAvailableTrainings(boolean filterByEmail) {
        Map<String, List<ScheduleServiceModel>> schedulesMap = new HashMap<>();
        scheduleRepository.findAll()
                .stream()
                .filter(schedule -> !filterByEmail || schedule.getUser().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
                .forEach(
                    schedule -> {
                        ScheduleServiceModel model = modelMapper.map(schedule, ScheduleServiceModel.class);
                        if(schedule.getUser().getSport() != null) {
                            model.setSportId(schedule.getUser().getSport().getId());
                            model.setSportName(schedule.getUser().getSport().getName());
                        }
                        if(userScheduleRepository.findByScheduleId(schedule.getId()).orElse(null) != null
                            || userRepository.getTrainingCount(SecurityContextHolder.getContext().getAuthentication().getName()) == 0) {
                            model.setIsActive(false);
                        }

                        String date = DateHelper.dateToString(model.getDate());
                        if (!schedulesMap.containsKey(date)) {
                            schedulesMap.put(date, new ArrayList<>());
                        }
                        schedulesMap.get(date).add(model);
                }
        );
        return mapToList(schedulesMap);
    }

    private List<ScheduleListServiceModel> mapToList(Map<String, List<ScheduleServiceModel>> schedulesMap) {
        List<ScheduleListServiceModel> scheduleList = new ArrayList<>();
        schedulesMap.forEach((key, value) -> {
            ScheduleListServiceModel scheduleListServiceModel = new ScheduleListServiceModel();
            scheduleListServiceModel.setScheduleList(value);
            scheduleListServiceModel.setDate(key);
            scheduleList.add(scheduleListServiceModel);
        });
        return scheduleList.stream().sorted(Comparator.comparing(ScheduleListServiceModel::getDate)).collect(Collectors.toList());
    }
}