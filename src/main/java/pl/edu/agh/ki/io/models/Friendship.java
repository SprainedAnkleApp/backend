package pl.edu.agh.ki.io.models;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "friendships")
public class Friendship {
    @Id
    @GeneratedValue
    @Getter
    private int id;

    @Column(name = "date_established")
    @Getter
    private Date dateEstablished;

    //0 - pending, 1 - accepted, 2 - declined, 3 - blocked
    @Column(name = "status")
    @Getter
    private int status;

    @ManyToOne
    @Getter
    private User requester;

    @ManyToOne
    @Getter
    private User addressee;
}
