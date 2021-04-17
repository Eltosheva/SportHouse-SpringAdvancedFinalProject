package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Place;
import com.eltosheva.sporthouse.models.service.ScheduleListServiceModel;
import com.eltosheva.sporthouse.models.service.ScheduleServiceModel;
import com.eltosheva.sporthouse.services.UserScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserScheduleServiceImpl implements UserScheduleService {

    private final EntityManagerFactory emf;

    @Override
    public List<ScheduleListServiceModel> findAllUserSchedules() {
        EntityManager em = emf.createEntityManager();

        List<Object[]> results = em.createQuery("select s.date as scheduleDate, s.startTime as startTime, " +
                " s.endTime as endTime, s.description as description, s.isActive as isActive, us.id as id," +
                " s.place as plase from UserSchedule us inner join Schedule s on us.scheduleId = s.id" +
                " inner join User u on us.user.id = u.id where u.email = ?1 ", Object[].class)
                .setParameter(1, SecurityContextHolder.getContext().getAuthentication().getName())
                .getResultList();

        List<ScheduleListServiceModel> scheduleList = new ArrayList<>();
        Map<String, List<ScheduleServiceModel>> scheduleMap = new HashMap<>();

        for (Object[] row : results) {
            ScheduleServiceModel scheduleServiceModel = new ScheduleServiceModel();
            scheduleServiceModel.setDate((LocalDate) row[0]);
            scheduleServiceModel.setStartTime((LocalTime) row[1]);
            scheduleServiceModel.setEndTime((LocalTime) row[2]);
            scheduleServiceModel.setDescription((String) row[3]);
            scheduleServiceModel.setId((String) row[5]);
            scheduleServiceModel.setPlace((Place) row[6]);

            String date = scheduleServiceModel.getDate().format(DateTimeFormatter.ofPattern("E, dd MMMM yyyy"));
            if (!scheduleMap.containsKey(date)) {
                scheduleMap.put(date, new ArrayList<>());
            }
            scheduleMap.get(date).add(scheduleServiceModel);
        }

        scheduleMap.forEach((key, value) -> {
            ScheduleListServiceModel scheduleListServiceModel = new ScheduleListServiceModel();
            scheduleListServiceModel.setScheduleList(value);
            scheduleListServiceModel.setDate(key);
            scheduleList.add(scheduleListServiceModel);
        });

        em.close();
        return scheduleList.stream().sorted(Comparator.comparing(ScheduleListServiceModel::getDate)).collect(Collectors.toList());
    }
}