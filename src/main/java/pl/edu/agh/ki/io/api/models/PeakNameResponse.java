package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;
import pl.edu.agh.ki.io.models.Peak;

@Data
@Builder
public class PeakNameResponse {
    private Long id;
    private String name;

    public static PeakNameResponse fromPeak(Peak peak) {
        return PeakNameResponse.builder()
                .id(peak.getId())
                .name(peak.getName())
                .build();
    }
}
