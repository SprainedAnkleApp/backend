package pl.edu.agh.ki.io.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "peak_completions")
public class PeakCompletion {
    @EmbeddedId
    private PeakCompletionKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @MapsId("peakId")
    @JoinColumn(name = "peak_id")
    @JsonIgnore
    private Peak peak;

    @Column(name = "completion_time", nullable = false)
    private Time completionTime;

    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @UpdateTimestamp
    @Column(name = "update_date", nullable = false)
    private Date updateDate;

    public PeakCompletion(PeakCompletionKey id, User user, Peak peak, Time completionTime){
        this.id = id;
        this.user = user;
        this.peak = peak;
        this.completionTime = completionTime;
    }

    @PrePersist
    protected void onCreate() {
        createDate = updateDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updateDate = new Date();
    }
}
