package pl.edu.agh.ki.io.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import pl.edu.agh.ki.io.models.wallElements.WallItem;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @ManyToOne
    User user;

    @ManyToOne
    WallItem wallItem;

    public Comment(User user, WallItem wallItem, String content) {
        this.wallItem = wallItem;
        this.user = user;
        this.content = content;
    }

    @Column(name = "content", nullable = false, length = 2047)
    private String content;

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
