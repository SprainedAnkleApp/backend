package pl.edu.agh.ki.io.models;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "peaks")
public class Peak {
    @Id
    @GeneratedValue
    @Column(name = "id")
    Long id;

    @Column(name="name")
    int name;

    @Column(name="height")
    int height;

    @Column(name="region")
    String region;

    @Column(name="about")
    String about;

    @Column(name="mountainRange")
    String mountainRange;

    @OneToMany(mappedBy = "peak")
    Set<PeakCompletion> peakCompletions;
}
