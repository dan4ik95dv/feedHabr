package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyAction;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

import name.sportivka.feed.model.MyDatabase;

/**
 * Created by daniil on 23.02.17.
 */
@Table(database = MyDatabase.class)
public class User extends BaseModel {
    @PrimaryKey
    @Column(name = "user_id")
    @Unique
    @SerializedName("id")
    long user_id;
    @Column
    @SerializedName("avatar")
    String avatar;
    @Column
    @ForeignKey(stubbedRelationship = true, onDelete = ForeignKeyAction.CASCADE, onUpdate = ForeignKeyAction.CASCADE)
    @SerializedName("counters")
    Counters counters;
    @Column
    @SerializedName("fullname")
    String fullName;
    @Column
    @ForeignKey(stubbedRelationship = true, onDelete = ForeignKeyAction.CASCADE, onUpdate = ForeignKeyAction.CASCADE)
    @SerializedName("geo")
    Geo geo;
    @Column
    @SerializedName("login")
    String login;
    @Column
    @SerializedName("rating")
    float rating;
    @Column
    @SerializedName("rating_position")
    String ratingPosition;
    @Column
    @SerializedName("is_readonly")
    boolean readOnly;
    @Column
    @SerializedName("score")
    float score;
    @Column
    @SerializedName("sex")
    int sex;
    @Column
    @SerializedName("time_registered")
    String timeRegistered;
    @Column
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
        return user_id;
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
