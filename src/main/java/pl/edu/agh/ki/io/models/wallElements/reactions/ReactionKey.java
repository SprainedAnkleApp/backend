package pl.edu.agh.ki.io.models.wallElements.reactions;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@Embeddable
@Getter
public class ReactionKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "wall_element_id")
    private Long wallElementID;
}
