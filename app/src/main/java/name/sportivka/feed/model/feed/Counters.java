package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import name.sportivka.feed.model.MyDatabase;

/**
 * Created by daniil on 23.02.17.
 */

@Table(database = MyDatabase.class)
public class Counters extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    long myId;
    @Column
    @SerializedName("comments")
    int comments;
    @Column
    @SerializedName("followed")
    int followed;
    @Column
    @SerializedName("followers")
    int followers;
    @Column
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
