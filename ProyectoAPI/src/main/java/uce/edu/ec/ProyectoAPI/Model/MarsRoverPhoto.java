package uce.edu.ec.ProyectoAPI.Model;

public class MarsRoverPhoto {
    private int photoid;
    private String imageUrl;
    private String earthDate;

    public String getImageUrl() {
        return imageUrl != null ? imageUrl.replace("http://", "https://") : imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public MarsRoverPhoto() {
    }

    public int getPhotoid() {
        return photoid;
    }

    public void setPhotoid(int photoid) {
        this.photoid = photoid;
    }

    public String getEarthDate() {
        return earthDate;
    }

    public void setEarthDate(String earthDate) {
        this.earthDate = earthDate;
    }
}
