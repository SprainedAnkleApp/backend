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
import java.util.HashSet;
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
    private String name;

    @Column(name="height", nullable = false)
    private int height;

    @Column(name="region", nullable = false)
    private String region;

    @Column(name="about", length = 1023)
    private String about;

    @Column(name="mountainRange", nullable = false)
    private String mountainRange;

    @Column(name = "photo", length = 1023, nullable = false)
    private String photo;

    @OneToMany(mappedBy = "peak")
    Set<PeakCompletion> peakCompletions = new HashSet<>();

    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @UpdateTimestamp
    @Column(name = "update_date", nullable = false)
    private Date updateDate;

    public Peak(String name, int height, String region, String about, String mountainRange, String photo) {
        this.name = name;
        this.height = height;
        this.region = region;
        this.about = about;
        this.mountainRange = mountainRange;
        this.photo = photo;
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
