package com.eltosheva.sporthouse.services;

import com.eltosheva.sporthouse.models.service.SportServiceModel;

import java.io.IOException;
import java.util.List;

public interface SportService {
    void addEditSport(SportServiceModel sportServiceModel);
    List<SportServiceModel> getSports();
    List<SportServiceModel> getSports(boolean showOnlyActive);
    void initSports() throws IOException;
    void changeStatus(String id);
}
