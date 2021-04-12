package pl.edu.agh.ki.io.models.wallElements;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.reactions.Reaction;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class WallItem {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Setter
    @ManyToOne
    private User user;

    @Column(name = "content", nullable = false, length = 2047)
    private String content;

    public WallItem(User user, String content){
        this.user = user;
        this.content = content;
    }

    @OneToMany
    private Set<Reaction> reactions;

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
