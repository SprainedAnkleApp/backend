package pl.edu.agh.ki.io.models.wallElements;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edu.agh.ki.io.models.Peak;
import pl.edu.agh.ki.io.models.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Entity
@NoArgsConstructor
@Table(name="peak_posts")
public class PeakPost extends WallItem{

    @ManyToOne
    private Peak peak;

    @Column(name = "photo_path", length = 1023)
    private String photoPath;

    public PeakPost(User user, String content, Peak peak) {
        super(user, content);
        this.peak = peak;
    }

    public PeakPost(User user, String content, Peak peak, double latitude, double longitude) {
        super(user, content, latitude, longitude);
        this.peak = peak;
    }

    public PeakPost(User user, String content, Peak peak, String photoPath) {
        super(user, content);
        this.peak = peak;
        this.photoPath = photoPath;
    }

    public PeakPost(User user, String content, Peak peak, double latitude, double longitude, String photoPath) {
        super(user, content, latitude, longitude);
        this.photoPath = photoPath;
        this.peak = peak;
    }
}
