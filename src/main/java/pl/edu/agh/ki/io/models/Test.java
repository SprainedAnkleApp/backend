package pl.edu.agh.ki.io.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name = "TEST")
public class Test {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @Getter
    @Setter
    private int id;

    @Column(name = "test")
    @Getter
    @Setter
    private String test;
}
