package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;
import pl.edu.agh.ki.io.models.PeakCompletion;
import pl.edu.agh.ki.io.models.PeakCompletionKey;

import java.time.Duration;
import java.util.Date;

@Data
@Builder
public class PeakCompletionResponse {
    private PeakCompletionKey id;
    private Long completionTime;  // duration of trip in minutes
    private Date completionDate;

    public static PeakCompletionResponse fromPeakCompletion(PeakCompletion completion) {
        return PeakCompletionResponse.builder()
                .id(completion.getId())
                .completionTime(completion.getCompletionTime().toMinutes())
                .completionDate(completion.getCreateDate())
                .build();
    }
}