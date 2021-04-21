package pl.edu.agh.ki.io.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.ki.io.validation.SimpleSqlProtected;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ConquerPeakRequest {

    @NotNull
    @SimpleSqlProtected
    private Long peakId;

    @NotNull
    @SimpleSqlProtected
    private int time;
}
