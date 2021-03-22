package pl.edu.agh.ki.io.models;

import lombok.Getter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Column(name="birthday")
    private Date birthday;

    @ManyToOne
    private Gender gender;

    @Column(name="phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "user")
    private Set<PeakCompletion> peakCompletions;

    @OneToMany()
    private Set<User> friends;
}
