package pl.edu.agh.ki.io.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SendMessageRequest {
    Long sendTo;
    Long senderId;

    @NotEmpty
    @NotNull
    String message;
}
