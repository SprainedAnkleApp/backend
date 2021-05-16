package pl.edu.agh.ki.io.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.ki.io.validation.SimpleSqlProtected;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PeakCompletionRequest {

    @NotNull
    private Long peakId;

    @NotNull
    private Long time;
}
