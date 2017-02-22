package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by daniil on 23.02.17.
 */


public class Comment {
    @SerializedName("author")
    User author;
    @SerializedName("avatar")
    String avatar;
    @SerializedName("is_can_vote")
    boolean canVote;
    @SerializedName("id")
    long id;
    @SerializedName("level")
    int level;
    @SerializedName("message")
    String message;
    @SerializedName("parent_id")
    long parentId;
    @SerializedName("score")
    int score;
    @SerializedName("time_changed")
    String timeChanged;
    @SerializedName("time_published")
    String timePublished;
    private Date requestTime;

    public User getAuthor() {
        return author;
    }

    public String getAvatar() {
        return avatar;
    }

    public boolean isCanVote() {
        return canVote;
    }

    public long getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public long getParentId() {
        return parentId;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public int getScore() {
        return score;
    }

    public String getTimeChanged() {
        return timeChanged;
    }

    public String getTimePublished() {
        return timePublished;
    }
}
