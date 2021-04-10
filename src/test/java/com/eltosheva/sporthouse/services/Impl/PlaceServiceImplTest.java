package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Place;
import com.eltosheva.sporthouse.models.service.PlaceServiceModel;
import com.eltosheva.sporthouse.repositories.PlaceRepository;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaceServiceImplTest {

    @Mock
    private PlaceServiceImpl placeService;

    @Mock
    private Resource placesFile;

    @Mock
    private PlaceRepository mockPlaceRepository;

    private ModelMapper modelMapper;

    private Path workingDir;

    @BeforeEach
    void setup() {
        this.workingDir = Path.of("", "src/test/resources");
        modelMapper = new ModelMapper();
        placeService = new PlaceServiceImpl(placesFile, modelMapper,
                mockPlaceRepository, new Gson());
    }


    @Test
    void addNewSportHall() {
        PlaceServiceModel testPlace = new PlaceServiceModel();
        testPlace.setName("test");
        testPlace.setCity("city");
        testPlace.setAddress("address");
        testPlace.setWorkFrom(LocalTime.now());
        testPlace.setWorkTo(LocalTime.now());
        testPlace.setImageUrl("some url");
        testPlace.setPhone("1234567890");
        testPlace.setDescription("description");

        placeService.addNewSportHall(testPlace);
        verify(mockPlaceRepository, times(1)).save(modelMapper.map(testPlace, Place.class));
    }

    @Test
    void getPlacesTest_whenPlaceExist_withData() {
        Place pOne = new Place();
        pOne.setId("ind1");
        pOne.setName("place1");

        Place pTwo = new Place();
        pTwo.setId("ind2");
        pTwo.setName("place2");

        Place pThree = new Place();
        pThree.setId("ind2");
        pThree.setName("place3");

        when(mockPlaceRepository.findAll()).thenReturn(List.of(pOne, pTwo, pThree));

        List<PlaceServiceModel> pList = placeService.getPlaces();

        assertEquals(3, pList.size());
        verify(mockPlaceRepository, times(1)).findAll();
    }

    @Test
    void removePlace() {
        placeService.removePlace("placeID");
        verify(mockPlaceRepository, times(1)).deleteById("placeID");
    }

    @Test
    void initPlaces_valid() throws IOException {
        // --> ????
    }

    @Test
    void initPlaces_illegalState() throws IOException {
        Path file = this.workingDir.resolve("wrong/places.json");
        when(mockPlaceRepository.count()).thenReturn(0L);
        when(placesFile.getURI()).thenReturn(file.toUri());

        Assertions.assertThrows(IllegalStateException.class, () -> placeService.initPlaces());
    }

    @Test
    void changeStatus_valid() {
        Place place = new Place();
        place.setIsActive(true);
        when(mockPlaceRepository.findById("id")).thenReturn(Optional.of(place));
        placeService.changeStatus("id");
        Assertions.assertEquals(place.getIsActive(), false);
    }

    @Test
    void changeStatus_illegalArgumentException() {
        when(mockPlaceRepository.findById("invalidId")).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class, () -> placeService.changeStatus("invalidId"));
    }

}