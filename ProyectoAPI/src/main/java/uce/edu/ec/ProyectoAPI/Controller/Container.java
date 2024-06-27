package uce.edu.ec.ProyectoAPI.Controller;

import uce.edu.ec.ProyectoAPI.Interface.MarsRover;
import uce.edu.ec.ProyectoAPI.Model.Curiosity;
import uce.edu.ec.ProyectoAPI.Model.MarsRoverPhoto;
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

    public MarsRover getCuriosity() {
        return new Curiosity(apiService);
    }

    public MarsRover getOpportunity() {
        return new Opportunity(apiService);
    }

    public MarsRover getSpirit() {
        return new Spirit(apiService);
    }

    public Map<String, List<MarsRoverPhoto>> fetchAndPrintPhotosByCameraAndSol(String roverName, String camera, int sol, JTextArea infoTextArea) {
        MarsRover rover = getRoverByName(roverName);
        if (rover != null) {
            Map<String, List<MarsRoverPhoto>> photos = rover.fetchPhotosByCameraAndSol(camera, sol);
            if (photos.isEmpty()) {
                infoTextArea.setText("\nNo se encontraron fotos para el rover " + roverName + ", cámara " + camera + " y sol " + sol + "\n");
            }
            return photos;
        } else {
            System.out.println("Rover not found: " + roverName);
            return null;
        }
    }
    

    private MarsRover getRoverByName(String roverName) {
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
