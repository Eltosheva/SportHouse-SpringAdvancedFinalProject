package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Sport;
import com.eltosheva.sporthouse.repositories.ProductRepository;
import com.eltosheva.sporthouse.repositories.SportRepository;
import com.eltosheva.sporthouse.services.SportService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SportServiceImplTest {

    private SportServiceImpl sportServiceImpl;
    @Mock
    private SportRepository sportRepository;
    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUpSports() {
        List<Sport> testSportList = new ArrayList<>();
    }

    @Test
    void addNewSport() {

    }

    @Test
    void getSportsValidTest() {
    }

    @Test
    void removeSport() {
    }
}