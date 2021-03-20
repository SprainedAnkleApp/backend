package pl.edu.agh.ki.io.models;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "genders")
public class Gender {
    @Id
    @Column(name = "id")
    @Getter
    private int id;

    @Column(name = "label")
    @Getter
    private String label;
}
