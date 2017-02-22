package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniil on 23.02.17.
 */

public class AddComment extends Simple {
    @SerializedName("comment")
    Comment comment;

    public Comment getComment() {
        return comment;
    }
}
