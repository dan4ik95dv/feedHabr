package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniil on 23.02.17.
 */


public class Counters {
    @SerializedName("comments")
    int comments;
    @SerializedName("followed")
    int followed;
    @SerializedName("followers")
    int followers;
    @SerializedName("posts")
    int posts;

    public int getComments() {
        return comments;
    }

    public int getFollowed() {
        return followed;
    }

    public int getFollowers() {
        return followers;
    }

    public int getPosts() {
        return posts;
    }
}
