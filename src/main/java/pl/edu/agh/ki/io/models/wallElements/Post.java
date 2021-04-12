package pl.edu.agh.ki.io.models.wallElements;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edu.agh.ki.io.models.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post extends WallItem {
    public Post(User user, String content){
        super(user, content);
    }
}
