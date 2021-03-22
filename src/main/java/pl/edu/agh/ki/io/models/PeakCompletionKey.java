package pl.edu.agh.ki.io.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@Embeddable
public class PeakCompletionKey implements Serializable {
    @Column(name = "user_id")
    @Getter
    private Long userId;

    @Column(name = "peak_id")
    @Getter
    private Long peakId;
}
