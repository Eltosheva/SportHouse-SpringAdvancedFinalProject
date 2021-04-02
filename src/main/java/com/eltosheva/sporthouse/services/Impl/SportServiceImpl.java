package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Sport;
import com.eltosheva.sporthouse.models.service.ProductServiceModel;
import com.eltosheva.sporthouse.models.service.SportServiceModel;
import com.eltosheva.sporthouse.repositories.ProductRepository;
import com.eltosheva.sporthouse.repositories.SportRepository;
import com.eltosheva.sporthouse.services.SportService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SportServiceImpl implements SportService {

    private final Resource sportsFile;
    private final SportRepository sportRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public SportServiceImpl(@Value("classpath:init/sports.json") Resource sportsFile, SportRepository sportRepository, ProductRepository productRepository, ModelMapper modelMapper, Gson gson) {
        this.sportsFile = sportsFile;
        this.sportRepository = sportRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void addNewSport(SportServiceModel sportServiceModel) {
        Sport sport = modelMapper.map(sportServiceModel, Sport.class);
        sport.setIsActive(true);
        sportRepository.saveAndFlush(sport);
    }

    @Override
    public List<SportServiceModel> getSports() {
        return sportRepository
                .findAll()
                .stream()
                .map(sport -> modelMapper.map(sport, SportServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void removeSport(String id) {

    }

    @Override
    public List<ProductServiceModel> getAllSportsAccessories(String sportId) {
        return null;
    }

    @Override
    public void initSports() {
        if (sportRepository.count() == 0) {
            try {
               Sport[] sportEntities =
                        gson.fromJson(Files.readString(Path.of(sportsFile.getURI())), Sport[].class);

                Arrays.stream(sportEntities).
                        forEach(sportRepository::save);
            } catch (IOException e) {
                throw new IllegalStateException("Sports cannot be initialized.");
            }
        }
    }
}