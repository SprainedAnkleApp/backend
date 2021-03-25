package pl.edu.agh.ki.io.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Time;


@NoArgsConstructor
@Entity
@Getter
@Table(name = "peak_completions")
public class PeakCompletion {
    @EmbeddedId
    PeakCompletionKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("peakId")
    @JoinColumn(name = "peak_id")
    private Peak peak;

    private Time completionTime;
}
