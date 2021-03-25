package pl.edu.agh.ki.io.models;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@NoArgsConstructor
@Entity
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "peaks")
public class Peak {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name="name", unique = true, nullable = false)
    int name;

    @Column(name="height", nullable = false)
    int height;

    @Column(name="region", nullable = false)
    String region;

    @Column(name="about")
    String about;

    @Column(name="mountainRange", nullable = false)
    String mountainRange;

    @OneToMany(mappedBy = "peak")
    Set<PeakCompletion> peakCompletions;

    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @UpdateTimestamp
    @Column(name = "update_date", nullable = false)
    private Date updateDate;

    @PrePersist
    protected void onCreate() {
        createDate = updateDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updateDate = new Date();
    }
}
