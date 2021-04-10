package com.eltosheva.sporthouse.services;

import com.eltosheva.sporthouse.models.service.ProductServiceModel;
import com.eltosheva.sporthouse.models.service.SportServiceModel;

import java.io.IOException;
import java.util.List;

public interface SportService {
    void addNewSport(SportServiceModel sportServiceModel);
    List<SportServiceModel> getSports();
    void initSports() throws IOException;
    void changeStatus(String id);
}
