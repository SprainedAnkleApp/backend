package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;
import pl.edu.agh.ki.io.models.Peak;

@Data
@Builder
public class PeakShortResponse {
    private Long id;
    private String name;
    private double latitude;
    private double longitude;

    public static PeakShortResponse fromPeak(Peak peak) {
        return PeakShortResponse.builder()
                .id(peak.getId())
                .name(peak.getName())
                .latitude(peak.getLatitude())
                .longitude(peak.getLongitude())
                .build();
    }
}
