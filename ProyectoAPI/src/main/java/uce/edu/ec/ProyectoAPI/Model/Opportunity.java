package uce.edu.ec.ProyectoAPI.Model;

import uce.edu.ec.ProyectoAPI.Service.ConsumerAPI;

import java.util.List;
import java.util.Map;

public class Opportunity implements uce.edu.ec.ProyectoAPI.Interface.MarsRover {

    private final ConsumerAPI consumerAPI;

    public Opportunity(ConsumerAPI consumerAPI) {
        this.consumerAPI = consumerAPI;
    }

    @Override
    public String getRoverName() {
        return "Opportunity";
    }

    @Override
    public Map<String, List<MarsRover>> fetchPhotosByCameraAndSol(String camera, int sol) {
        return consumerAPI.getPhotosByRoverCameraAndSol(getRoverName().toLowerCase(), camera, sol);
    }


}
