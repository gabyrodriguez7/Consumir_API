package uce.edu.ec.ProyectoAPI.Model;

import uce.edu.ec.ProyectoAPI.Interface.IMarsRover;
import uce.edu.ec.ProyectoAPI.Service.ConsumerAPI;

import java.util.List;
import java.util.Map;

public class Spirit implements IMarsRover {

    private final ConsumerAPI consumerAPI;

    public Spirit(ConsumerAPI consumerAPI) {
        this.consumerAPI = consumerAPI;
    }

    @Override
    public String getRoverName() {
        return "Spirit";
    }

    @Override
    public Map<String, List<MarsFilter>> fetchPhotosByCameraAndSol(String camera, int sol) {
        return consumerAPI.getPhotosByRoverCameraAndSol(getRoverName().toLowerCase(), camera, sol);
    }


}
