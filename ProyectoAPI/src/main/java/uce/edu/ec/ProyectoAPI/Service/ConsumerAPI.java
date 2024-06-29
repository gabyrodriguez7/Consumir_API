package uce.edu.ec.ProyectoAPI.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import uce.edu.ec.ProyectoAPI.Model.MarsFilter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsumerAPI {

    private static final String API_KEY = "yMc3HR72ig1U67cbSe2zoMfOFTDGPGYx2a1ozpy4"; // Reemplazar con tu API key de NASA
    private static final String BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers";
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public ConsumerAPI() {
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public Map<String, List<MarsFilter>> getPhotosByRoverCameraAndSol(String rover, String camera, int sol) {
        Map<String, List<MarsFilter>> photosByCamera = new HashMap<>();
        try {
            String url = String.format("%s/%s/photos?sol=%d&camera=%s&api_key=%s", BASE_URL, rover, sol, camera, API_KEY);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode root = objectMapper.readTree(response.body());
                JsonNode photosNode = root.path("photos");

                for (JsonNode photoNode : photosNode) {
                    MarsFilter photo = new MarsFilter();
                    photo.setPhotoId(photoNode.path("id").asInt());
                    photo.setImageUrl(photoNode.path("img_src").asText());
                    photo.setEarthDate(photoNode.path("earth_date").asText());

                    // Solo agregar la foto si tiene la cámara especificada
                    String cameraName = photoNode.path("camera").path("name").asText();
                    if (cameraName.equalsIgnoreCase(camera)) {
                        photosByCamera.computeIfAbsent(cameraName, k -> new ArrayList<>()).add(photo);
                    }
                }
            } else {
                System.err.println("Error: " + response.statusCode() + " - " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return photosByCamera;
    }
}
