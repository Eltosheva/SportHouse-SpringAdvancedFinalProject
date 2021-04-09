package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.bindingModels.PlaceBindingModel;
import com.eltosheva.sporthouse.models.entities.Place;
import com.eltosheva.sporthouse.models.service.PlaceServiceModel;
import com.eltosheva.sporthouse.repositories.PlaceRepository;
import com.eltosheva.sporthouse.services.PlaceService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class  PlaceServiceImpl implements PlaceService {

    private final Resource placesFile;
    private final ModelMapper modelMapper;
    private final PlaceRepository placeRepository;
    private final Gson gson;

    public PlaceServiceImpl(@Value("classpath:init/places.json") Resource placesFile, ModelMapper modelMapper, PlaceRepository placeRepository, Gson gson) {
        this.placesFile = placesFile;
        this.modelMapper = modelMapper;
        this.placeRepository = placeRepository;
        this.gson = gson;
    }

    @Override
    public void addNewSportHall(PlaceServiceModel placeServiceModel) {
        Place place = modelMapper.map(placeServiceModel, Place.class);
        placeRepository.save(place);
    }

    @Override
    public List<PlaceServiceModel> getPlaces() {
        List<PlaceServiceModel> placeServiceModels = new ArrayList<>();
        placeRepository
                .findAll()
                .stream()
                .forEach(place -> {
                    PlaceServiceModel placeServiceModel = new PlaceServiceModel();
                    modelMapper.map(place, placeServiceModel);
                    placeServiceModels.add(placeServiceModel);
                });
        return placeServiceModels;
    }

    @Override
    public void removePlace(String id) {
        placeRepository.deleteById(id);
    }

    @Override
    public void initPlaces() {
        if (placeRepository.count() == 0) {
            try {
                Place[] placeEntities =
                        gson.fromJson(Files.readString(Path.of(placesFile.getURI())), Place[].class);

                Arrays.stream(placeEntities).
                        forEach(placeRepository::save);
            } catch (IOException e) {
                throw new IllegalStateException("Places cannot be initialized.");
            }
        }
    }

    @Override
    public void changeStatus(String id) {
        Place place = placeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        place.setIsActive(!place.getIsActive());
        placeRepository.saveAndFlush(place);
    }
}
