package uce.edu.ec.ProyectoAPI.Interface;

import uce.edu.ec.ProyectoAPI.Model.MarsFilter;

import java.util.List;
import java.util.Map;

public interface IMarsRover {
    String getRoverName();
    Map<String, List<MarsFilter>> fetchPhotosByCameraAndSol(String camera, int sol);
}
