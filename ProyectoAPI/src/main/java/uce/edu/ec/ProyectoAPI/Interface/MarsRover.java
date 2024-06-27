package uce.edu.ec.ProyectoAPI.Interface;

import java.util.List;
import java.util.Map;

public interface MarsRover {
    String getRoverName();
    Map<String, List<uce.edu.ec.ProyectoAPI.Model.MarsRover>> fetchPhotosByCameraAndSol(String camera, int sol);
}
