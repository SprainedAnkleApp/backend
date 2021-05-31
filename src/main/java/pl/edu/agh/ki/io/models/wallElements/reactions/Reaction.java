package pl.edu.agh.ki.io.models.wallElements.reactions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "reactions")
public class Reaction {
    @EmbeddedId
    private ReactionKey id;

    @Column(name = "type", nullable = false)
    private ReactionType type;

    public Reaction(ReactionKey id, ReactionType type) {
        this.id = id;
        this.type = type;
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
