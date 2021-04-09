package pl.edu.agh.ki.io.models.wallElements;


import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edu.agh.ki.io.models.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "photos")
public class Photo extends WallItem {
    @Column(name = "photo_path", nullable = false, length = 1023)
    private String photoPath;

    public Photo(User user, String content, String photoPath){
        super(user, content);
        this.photoPath = photoPath;
    }
}
