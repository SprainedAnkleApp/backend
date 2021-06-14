package pl.edu.agh.ki.io.api.models;

import pl.edu.agh.ki.io.models.wallElements.PeakPost;
import pl.edu.agh.ki.io.models.wallElements.Photo;
import pl.edu.agh.ki.io.models.wallElements.WallItem;
import pl.edu.agh.ki.io.models.wallElements.reactions.Reaction;

import java.io.IOException;
import java.util.List;

public abstract class WallItemResponse {
    public static WallItemResponse fromWallItemAndReactions(WallItem wallItem, List<Reaction> reactions)  {
        switch (wallItem.getClass().getSimpleName()) {
            case "Post":
                return PostResponse.fromPostAndReactions(wallItem, reactions);
            case "Photo":
                return PhotoResponse.fromPhotoAndReactions((Photo) wallItem, reactions);
            case "PeakPost":
                return PeakPostResponse.fromPeakPostAndReactions((PeakPost) wallItem, reactions);
        }
        return null;
    }
}
