package pl.edu.agh.ki.io.models;

import lombok.Getter;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "peak_completions")
public class PeakCompletion {
    @EmbeddedId
    PeakCompletionKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @Getter
    private User user;

    @ManyToOne
    @MapsId("peakId")
    @JoinColumn(name = "peak_id")
    @Getter
    private Peak peak;

    @Column(name = "completion_time")
    @Getter
    private Time completionTime;
}
