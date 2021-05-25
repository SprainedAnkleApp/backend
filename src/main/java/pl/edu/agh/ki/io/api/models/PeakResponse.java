package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;
import pl.edu.agh.ki.io.models.Peak;


@Data
@Builder
public class PeakResponse {
    private Long id;
    private String name;
    private int height;
    private String region;
    private String about;
    private String mountainRange;
    private double latitude;
    private double longitude;
    private String photo;
    private boolean completed;

    public static PeakResponse fromPeak(Peak peak) {
        return PeakResponse.builder()
                .id(peak.getId())
                .name(peak.getName())
                .height(peak.getHeight())
                .region(peak.getRegion())
                .about(peak.getAbout())
                .mountainRange(peak.getMountainRange())
                .latitude(peak.getLatitude())
                .longitude(peak.getLongitude())
                .photo(peak.getPhoto())
                .build();
    }

    public static PeakResponse fromPeakWithCompletion(Peak peak, boolean completed) {
        return PeakResponse.builder()
                .id(peak.getId())
                .name(peak.getName())
                .height(peak.getHeight())
                .region(peak.getRegion())
                .about(peak.getAbout())
                .mountainRange(peak.getMountainRange())
                .latitude(peak.getLatitude())
                .longitude(peak.getLongitude())
                .photo(peak.getPhoto())
                .completed(completed)
                .build();
    }
}
