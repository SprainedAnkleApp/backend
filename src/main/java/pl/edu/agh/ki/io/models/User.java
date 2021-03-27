package pl.edu.agh.ki.io.models;


import ch.qos.logback.core.net.server.Client;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name="email", unique = true, nullable = false)
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
    private Set<PeakCompletion> peakCompletions = new HashSet<>();

    @OneToMany()
    private Set<User> friends = new HashSet<>();

    public User(String login, String password, String firstName, String lastName, String email, String profilePhoto,
                  Date birthday, Gender gender, String phoneNumber) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profilePhoto = profilePhoto;
        this.birthday = birthday;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    //TODO: probably add this flags to model
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    private java.util.Date createDate;

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
