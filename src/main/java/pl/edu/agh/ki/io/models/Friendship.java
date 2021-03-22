package pl.edu.agh.ki.io.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "friendships")
public class Friendship {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "date_established")
    private Date dateEstablished;

    //0 - pending, 1 - accepted, 2 - declined, 3 - blocked
    @Column(name = "status")
    private int status;

    @ManyToOne
    private User requester;

    @ManyToOne
    private User addressee;
}
