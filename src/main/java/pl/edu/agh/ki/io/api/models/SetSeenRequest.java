package pl.edu.agh.ki.io.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SetSeenRequest {
    private Long messageId;
}
