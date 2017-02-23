package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

import name.sportivka.feed.model.MyDatabase;

/**
 * Created by daniil on 23.02.17.
 */

@Table(database = MyDatabase.class)
public class Comment extends BaseModel {
    @Column
    @PrimaryKey
    @SerializedName("id")
    long id;
    @Column
    @SerializedName("author")
    User author;
    @Column
    @SerializedName("avatar")
    String avatar;
    @Column
    @SerializedName("is_can_vote")
    boolean canVote;
    @Column
    @SerializedName("level")
    int level;
    @Column
    @SerializedName("message")
    String message;
    @Column
    @SerializedName("parent_id")
    long parentId;
    @Column
    @SerializedName("score")
    int score;
    @Column
    @SerializedName("time_changed")
    String timeChanged;
    @Column
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
