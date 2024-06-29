package uce.edu.ec.ProyectoAPI.Controller;

import uce.edu.ec.ProyectoAPI.Interface.IMarsRover;
import uce.edu.ec.ProyectoAPI.Model.Curiosity;
import uce.edu.ec.ProyectoAPI.Model.MarsFilter;
import uce.edu.ec.ProyectoAPI.Model.Opportunity;
import uce.edu.ec.ProyectoAPI.Model.Spirit;
import uce.edu.ec.ProyectoAPI.Service.ConsumerAPI;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class Container {
    private final ConsumerAPI apiService;

    public Container() {
        this.apiService = new ConsumerAPI();
    }

    public IMarsRover getCuriosity() {
        return new Curiosity(apiService);
    }

    public IMarsRover getOpportunity() {
        return new Opportunity(apiService);
    }

    public IMarsRover getSpirit() {
        return new Spirit(apiService);
    }

    public Map<String, List<MarsFilter>> fetchAndPrintPhotosByCameraAndSol(String roverName, String camera, int sol, JTextArea infoTextArea) {
        IMarsRover rover = getRoverByName(roverName);
        if (rover != null) {
            Map<String, List<MarsFilter>> photos = rover.fetchPhotosByCameraAndSol(camera, sol);
            if (photos.isEmpty()) {
                infoTextArea.setText("\nNo se encontraron fotos para el rover " + roverName + ", camara " + camera + " y sol " + sol + "\n");
            }
            return photos;
        } else {
            return null;
        }
    }
    

    private IMarsRover getRoverByName(String roverName) {
        switch (roverName.toLowerCase()) {
            case "curiosity":
                return getCuriosity();
            case "opportunity":
                return getOpportunity();
            case "spirit":
                return getSpirit();
            default:
                return null;
        }
    }
}
