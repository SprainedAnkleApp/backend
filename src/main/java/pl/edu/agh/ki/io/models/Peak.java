package pl.edu.agh.ki.io.models;


import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "peaks")
public class Peak {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @Getter
    private Long id;

    @Column(name="name")
    @Getter
    private int name;

    @Column(name="height")
    @Getter
    private int height;

    @Column(name="region")
    @Getter
    private String region;

    @Column(name="about")
    @Getter
    private String about;

    @Column(name="mountainRange")
    @Getter
    private String mountainRange;

    @OneToMany(mappedBy = "peaks")
    @Getter
    private Set<PeakCompletion> peakCompletions;
}
