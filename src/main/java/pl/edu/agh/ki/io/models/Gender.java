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
@Table(name = "genders")
public class Gender {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "label", unique = true, nullable = false)
    private String label;

    public Gender(String label){
        this.label = label;
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
