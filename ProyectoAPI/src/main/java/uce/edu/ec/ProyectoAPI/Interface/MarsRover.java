package uce.edu.ec.ProyectoAPI.Interface;

import uce.edu.ec.ProyectoAPI.Model.MarsRoverPhoto;

import java.util.List;
import java.util.Map;

public interface MarsRover {
    String getRoverName();
    Map<String, List<MarsRoverPhoto>> fetchPhotosByCameraAndSol(String camera, int sol);
}
