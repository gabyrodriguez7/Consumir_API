package uce.edu.ec.ProyectoAPI.Model;

import uce.edu.ec.ProyectoAPI.Interface.IMarsRover;
import uce.edu.ec.ProyectoAPI.Service.ConsumerAPI;

import java.util.List;
import java.util.Map;

public class Curiosity implements IMarsRover {

    private final ConsumerAPI consumerAPI;

    public Curiosity(ConsumerAPI consumerAPI) {
        this.consumerAPI = consumerAPI;
    }

    @Override
    public String getRoverName() {
        return "Curiosity";
    }

    @Override
    public Map<String, List<MarsFilter>> fetchPhotosByCameraAndSol(String camera, int sol) {
        return consumerAPI.getPhotosByRoverCameraAndSol("curiosity", camera, sol);
    }


}
