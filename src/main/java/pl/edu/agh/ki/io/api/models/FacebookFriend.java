package pl.edu.agh.ki.io.api.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FacebookFriend {
    public String name;
    public String id;

    @Override
    public String toString() {
        return "FacebookUser{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
