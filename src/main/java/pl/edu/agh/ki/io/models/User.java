package pl.edu.agh.ki.io.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import pl.edu.agh.ki.io.models.wallElements.WallItem;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @Column(name = "facebook_user_id")
    private String facebookUserId;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="auth_provider", nullable = false)
    private AuthProvider authProvider;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name="email", unique = true, nullable = false)
    private String email;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Column(name = "background_photo")
    private String backgroundPhoto = "https://images.pexels.com/photos/572897/pexels-photo-572897.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260";

    @Column(name="birthday")
    private Date birthday;

    @Column(name="about", length = 1023)
    private String about;

    @Column(name="phone_number")
    private String phoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<PeakCompletion> peakCompletions = new HashSet<>();

    @JsonIgnore
    @OneToMany()
    private Set<User> friends = new HashSet<>();

    @JsonIgnore
    @OneToMany
    private  Set<WallItem> wallItems = new HashSet<>();


    public User(String login, String password, AuthProvider authProvider, String firstName, String lastName, String email, String profilePhoto,
                Date birthday, String phoneNumber) {
        this(login, password, authProvider, firstName, lastName, email);
        this.profilePhoto = profilePhoto;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }

    public User(String login, String password, AuthProvider authProvider, String firstName, String lastName, String email) {
        this.login = login;
        this.password = password;
        this.authProvider = authProvider;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    private java.util.Date createDate;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "update_date", nullable = false)
    private java.util.Date updateDate;

    @PrePersist
    protected void onCreate() {
        createDate = updateDate = new java.util.Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updateDate = new java.util.Date();
    }
}
