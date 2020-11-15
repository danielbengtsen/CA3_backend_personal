package dto;

public class ServicepointsResponseDTO {
    private PostnordResponseDTO postnord;
    private WeatherResponseDTO weather;
    private SportResponseDTO sport;

    public ServicepointsResponseDTO(PostnordResponseDTO postnord, WeatherResponseDTO weather, SportResponseDTO sport) {
        this.postnord = postnord;
        this.weather = weather;
        this.sport = sport;
    }

    public PostnordResponseDTO getPostnord() {
        return postnord;
    }

    public void setPostnord(PostnordResponseDTO postnord) {
        this.postnord = postnord;
    }

    public WeatherResponseDTO getWeather() {
        return weather;
    }

    public void setWeather(WeatherResponseDTO weather) {
        this.weather = weather;
    }

    public SportResponseDTO getSport() {
        return sport;
    }

    public void setSport(SportResponseDTO sport) {
        this.sport = sport;
    }
}
