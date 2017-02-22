package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniil on 23.02.17.
 */

public class User {
    @SerializedName("avatar")
    String avatar;
    @SerializedName("counters")
    Counters counters;
    @SerializedName("fullname")
    String fullName;
    @SerializedName("geo")
    Geo geo;
    @SerializedName("id")
    long id;
    @SerializedName("login")
    String login;
    @SerializedName("rating")
    float rating;
    @SerializedName("rating_position")
    String ratingPosition;
    @SerializedName("is_readonly")
    boolean readOnly;
    @SerializedName("score")
    float score;
    @SerializedName("sex")
    int sex;
    @SerializedName("time_registered")
    String timeRegistered;
    @SerializedName("vote")
    int vote;

    public String getAvatar() {
        return avatar;
    }

    public Counters getCounters() {
        return counters;
    }

    public String getFullName() {
        return fullName;
    }

    public Geo getGeo() {
        return geo;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public float getRating() {
        return rating;
    }

    public String getRatingPosition() {
        return ratingPosition;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public float getScore() {
        return score;
    }

    public int getSex() {
        return sex;
    }

    public String getTimeRegistered() {
        return timeRegistered;
    }

    public int getVote() {
        return vote;
    }
}
