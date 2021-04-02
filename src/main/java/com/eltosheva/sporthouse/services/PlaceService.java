package com.eltosheva.sporthouse.services;

import com.eltosheva.sporthouse.models.service.PlaceServiceModel;
import java.util.List;

public interface PlaceService {

    void addNewSportHall(PlaceServiceModel placeServiceModel);
    List<PlaceServiceModel> getPlaces();
    void removePlace(String id);
    void initPlaces();
}
