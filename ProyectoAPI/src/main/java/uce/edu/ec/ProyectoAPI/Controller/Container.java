package uce.edu.ec.ProyectoAPI.Controller;

import uce.edu.ec.ProyectoAPI.Model.Curiosity;
import uce.edu.ec.ProyectoAPI.Model.MarsRover;
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

    public uce.edu.ec.ProyectoAPI.Interface.MarsRover getCuriosity() {
        return new Curiosity(apiService);
    }

    public uce.edu.ec.ProyectoAPI.Interface.MarsRover getOpportunity() {
        return new Opportunity(apiService);
    }

    public uce.edu.ec.ProyectoAPI.Interface.MarsRover getSpirit() {
        return new Spirit(apiService);
    }

    public Map<String, List<MarsRover>> fetchAndPrintPhotosByCameraAndSol(String roverName, String camera, int sol, JTextArea infoTextArea) {
        uce.edu.ec.ProyectoAPI.Interface.MarsRover rover = getRoverByName(roverName);
        if (rover != null) {
            Map<String, List<MarsRover>> photos = rover.fetchPhotosByCameraAndSol(camera, sol);
            if (photos.isEmpty()) {
                infoTextArea.setText("\nNo se encontraron fotos para el rover " + roverName + ", camara " + camera + " y sol " + sol + "\n");
            }
            return photos;
        } else {
            return null;
        }
    }
    

    private uce.edu.ec.ProyectoAPI.Interface.MarsRover getRoverByName(String roverName) {
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
