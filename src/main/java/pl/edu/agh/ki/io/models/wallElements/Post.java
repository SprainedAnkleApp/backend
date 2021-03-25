package pl.edu.agh.ki.io.models.wallElements;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post extends WallItem {

}
