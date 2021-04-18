package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.*;
import com.eltosheva.sporthouse.models.service.ScheduleListServiceModel;
import com.eltosheva.sporthouse.models.service.ScheduleServiceModel;
import com.eltosheva.sporthouse.repositories.PlaceRepository;
import com.eltosheva.sporthouse.repositories.ScheduleRepository;
import com.eltosheva.sporthouse.repositories.UserRepository;
import com.eltosheva.sporthouse.repositories.UserScheduleRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceImplTest {

    private ScheduleServiceImpl scheduleService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private PlaceRepository placeRepository;
    @Mock
    private UserScheduleRepository userScheduleRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {

        scheduleService = new ScheduleServiceImpl(userRepository, scheduleRepository, placeRepository, modelMapper, userScheduleRepository);

        User u1 = new User();
        u1.setId("123");
        u1.setEmail("1@1");
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "1@1";
            }
        });
    }

    @Test
    void addRecord() {
        User u = new User();
        u.setId("2233");
        u.setEmail("1@1");

        Place place = new Place();
        place.setId("111122");

        ScheduleServiceModel scheduleServiceModel = new ScheduleServiceModel();
        scheduleServiceModel.setPlaceId("111122");
        scheduleServiceModel.setIsActive(true);
        scheduleServiceModel.setPlace(place);
        scheduleServiceModel.setId(null);
        Schedule schedule = modelMapper.map(scheduleServiceModel, Schedule.class);
        schedule.setUser(u);

        when(userRepository.findByEmail("1@1")).thenReturn(Optional.of(u));
        when(placeRepository.findById("111122")).thenReturn(Optional.of(place));

        scheduleService.addRecord(scheduleServiceModel);

        verify(scheduleRepository, times(1)).saveAndFlush(schedule);

    }

    @Test
    void findAllAvailableTrainings() {

        Sport sp = new Sport();
        sp.setId("123");
        sp.setName("Tennis");

        User u = new User();
        u.setEmail("1@1");
        u.setId("1111");
        u.setSport(sp);

        Schedule sch = new Schedule();
        sch.setId("222");
        sch.setUser(u);
        sch.setDate(LocalDate.now());

        UserSchedule usched = new UserSchedule();

        when(scheduleRepository.findAll()).thenReturn(List.of(sch));

        when(userScheduleRepository.findByScheduleId("222")).thenReturn(Optional.of(usched));
//        when(userRepository.getTrainingCount("1111")).thenReturn(2);


        List<ScheduleListServiceModel> result = scheduleService.findAllByUser_email();
        assertEquals(1, result.size());
    }
}