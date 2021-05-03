package pl.edu.agh.ki.io.models.wallElements;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edu.agh.ki.io.models.Peak;
import pl.edu.agh.ki.io.models.User;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name="peak_posts")
public class PeakPost extends WallItem{

    @ManyToOne
    private Peak peak;

    public PeakPost(User user, String content, Peak peak) {
        super(user, content);
        this.peak = peak;
    }
}
