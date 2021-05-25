package pl.edu.agh.ki.io.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@NoArgsConstructor
@Entity
@Getter
@Table(name = "friendships")
public class Friendship {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    //0 - pending, 1 - accepted, 2 - blocked
    @Column(name = "status", nullable = false)
    private int status;

    @ManyToOne
    private User requester;

    @ManyToOne
    private User addressee;

    public Friendship(int status, User requester, User addressee) {
        this.status = status;
        this.requester = requester;
        this.addressee = addressee;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @UpdateTimestamp
    @Column(name = "update_date", nullable = false)
    private Date updateDate;

    @PrePersist
    protected void onCreate() {
        createDate = updateDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updateDate = new Date();
    }
}
