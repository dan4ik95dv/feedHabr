package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

import name.sportivka.feed.model.MyDatabase;

/**
 * Created by daniil on 23.02.17.
 */

@Table(database = MyDatabase.class)
public class Hub extends BaseModel {
    @Column
    @PrimaryKey
    @Unique
    @SerializedName("id")
    long myId;
    @Column
    @SerializedName("about")
    String about;
    @Column
    @SerializedName("alias")
    String alias;
    @Column
    @SerializedName("is_company")
    boolean company;
    @Column
    @SerializedName("count_posts")
    int countPosts;
    @Column
    @SerializedName("count_subscribers")
    int countSubscribers;

    @Column
    @SerializedName("is_membership")
    boolean membership;
    @Column
    @SerializedName("is_profiled")
    boolean profiled;
    @Column
    @SerializedName("rating")
    float rating;
    @Column
    @SerializedName("tags_string")
    String tagsString;
    @Column
    @SerializedName("title")
    String title;
    @Column
    String category;
    @Column
    @ForeignKey(saveForeignKeyModel = false, references = @ForeignKeyReference(columnName = "ref_flow_id", foreignKeyColumnName = "flow_id"))
    @SerializedName("flow")
    Flow flow;

    public Flow getFlow() {
        return flow;
    }

    public void setFlow(Flow flow) {
        this.flow = flow;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAbout() {
        return about;
    }

    public String getAlias() {
        return alias;
    }

    public boolean isCompany() {
        return company;
    }

    public int getCountPosts() {
        return countPosts;
    }

    public int getCountSubscribers() {
        return countSubscribers;
    }

    public long getId() {
        return myId;
    }

    public boolean isMembership() {
        return membership;
    }

    public boolean isProfiled() {
        return profiled;
    }

    public float getRating() {
        return rating;
    }

    public String getTagsString() {
        return tagsString;
    }

    public String getTitle() {
        return title;
    }
}
