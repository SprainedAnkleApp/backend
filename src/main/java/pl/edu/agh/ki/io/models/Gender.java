package pl.edu.agh.ki.io.models;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "genders")
public class Gender {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "label")
    private String label;
}
