package uce.edu.ec.ProyectoAPI.Model;

import uce.edu.ec.ProyectoAPI.Interface.MarsRover;
import uce.edu.ec.ProyectoAPI.Service.ConsumerAPI;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Opportunity implements MarsRover {

    private final ConsumerAPI consumerAPI;

    public Opportunity(ConsumerAPI consumerAPI) {
        this.consumerAPI = consumerAPI;
    }

    @Override
    public String getRoverName() {
        return "Opportunity";
    }

    @Override
    public Map<String, List<MarsRoverPhoto>> fetchPhotosByCameraAndSol(String camera, int sol) {
        return consumerAPI.getPhotosByRoverCameraAndSol(getRoverName().toLowerCase(), camera, sol);
    }


}
