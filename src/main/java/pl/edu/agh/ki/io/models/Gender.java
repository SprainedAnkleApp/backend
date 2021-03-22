package pl.edu.agh.ki.io.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "genders")
public class Gender {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "label")
    private String label;

    public Gender(String label){
        this.label = label;
    }
}
