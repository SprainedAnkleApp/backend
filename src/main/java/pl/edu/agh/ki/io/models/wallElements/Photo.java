package pl.edu.agh.ki.io.models.wallElements;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.agh.ki.io.models.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "photos")
public class Photo extends WallItem {
    @Column(name = "photo_path", nullable = false, length = 1023)
    private String photoPath;

    public Photo(User user, String content, String photoPath) {
        super(user, content);
        this.photoPath = photoPath;
    }

    public Photo(User user, String content, String photoPath, double latitude, double longitude){
        super(user, content, latitude, longitude);
        this.photoPath = photoPath;
    }
}
