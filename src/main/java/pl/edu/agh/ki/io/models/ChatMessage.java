package pl.edu.agh.ki.io.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "messages")
public class ChatMessage {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    @Column(name = "message", nullable = false, length = 2047)
    private String message;

    private boolean seen;

    public ChatMessage(User sender, User receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "update_date", nullable = false)
    private Date updateDate;

    @PrePersist
    protected void onCreate() {
        createDate = updateDate = new Date();
        seen = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updateDate = new Date();
    }
}
