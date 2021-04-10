package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Sport;
import com.eltosheva.sporthouse.models.service.SportServiceModel;
import com.eltosheva.sporthouse.repositories.ProductRepository;
import com.eltosheva.sporthouse.repositories.SportRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SportServiceImplTest {

    private SportServiceImpl sportServiceImpl;
    @Mock
    private Resource sportsFile;
    @Mock
    private SportRepository sportRepository;
    @Mock
    private ProductRepository productRepository;

    private ModelMapper modelMapper;

    private Path workingDir;
    @BeforeEach
    void setup() {
        this.workingDir = Path.of("", "src/test/resources");
        modelMapper = new ModelMapper();
        sportServiceImpl = new SportServiceImpl(sportsFile, sportRepository,
                productRepository, modelMapper, new Gson());
    }

    @Test
    void addNewSport() {
       SportServiceModel sportServiceModel = new SportServiceModel();
       sportServiceModel.setName("Tennis");
       sportServiceModel.setIsActive(true);
       sportServiceImpl.addNewSport(sportServiceModel);
       verify(sportRepository, times(1)).saveAndFlush(modelMapper.map(sportServiceModel, Sport.class));
    }

    @Test
    void getSportsValidTest() {
        Sport s1 = new Sport();
        s1.setId("123");
        s1.setName("Tennis");

        Sport s2 = new Sport();
        s2.setId("123");
        s2.setName("Football");

        when(sportRepository.findAll()).thenReturn(List.of(s1, s2));

        List<SportServiceModel> spList = sportServiceImpl.getSports();
        assertEquals(2, spList.size());
    }

    @Test
    void initSports_valid() throws IOException {
        // --> ????
    }

    @Test
    void initSports_illegalState() throws IOException {
        Path file = this.workingDir.resolve("wrong/places.json");
        when(sportRepository.count()).thenReturn(0L);
        when(sportsFile.getURI()).thenReturn(file.toUri());

        Assertions.assertThrows(IllegalStateException.class, () -> sportServiceImpl.initSports());
    }

    @Test
    void changeStatus_valid() {
        Sport s1 = new Sport();
        s1.setIsActive(true);

        when(sportRepository.findById("id")).thenReturn(Optional.of(s1));
        sportServiceImpl.changeStatus("id");
        Assertions.assertEquals(s1.getIsActive(), false);
    }

    @Test
    void changeStatus_illegalArgs() {
        when(sportRepository.findById("invalidId")).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class, () -> sportServiceImpl.changeStatus("invalidId"));
    }
}