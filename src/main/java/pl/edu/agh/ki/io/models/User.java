package pl.edu.agh.ki.io.models;

import lombok.Getter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    @Getter
    private String login;

    @Column(name = "password")
    @Getter
    private String password;

    @Column(name="first_name")
    @Getter
    private String firstName;

    @Column(name="last_name")
    @Getter
    private String lastName;

    @Column(name="email")
    @Getter
    private String email;

    @Column(name = "profile_photo")
    @Getter
    private String profilePhoto;

    @Column(name="birthday")
    @Getter
    private Date birthday;

    @ManyToOne
    @Getter
    private Gender gender;

    @Column(name="phone_number")
    @Getter
    private String phoneNumber;

    @OneToMany(mappedBy = "users")
    @Getter
    private Set<PeakCompletion> peakCompletions;

    @OneToMany(mappedBy = "users")
    @Getter
    private Set<User> friends;
}
