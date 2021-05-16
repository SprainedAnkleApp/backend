package pl.edu.agh.ki.io.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookFriendList {
    public List<FacebookFriend> data;
    public String next; // next page address
}
