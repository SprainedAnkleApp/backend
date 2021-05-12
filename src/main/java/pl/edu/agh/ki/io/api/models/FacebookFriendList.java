package pl.edu.agh.ki.io.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookFriendList {
    public List<FacebookFriend> data;
}
