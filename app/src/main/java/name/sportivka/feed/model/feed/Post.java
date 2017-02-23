package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;
import java.util.List;

import name.sportivka.feed.model.MyDatabase;

/**
 * Created by daniil on 23.02.17.
 */

@Table(database = MyDatabase.class)
public class Post extends BaseModel {
    @Column
    @PrimaryKey
    @SerializedName("id")
    long id;
    @Column
    @ForeignKey(saveForeignKeyModel = false)
    @SerializedName("author")
    User author;
    @Column
    @SerializedName("is_can_vote")
    boolean canVote;
    @Column
    @SerializedName("comments_count")
    int commentsCount;
    @Column
    @SerializedName("is_favorite")
    boolean favorite;
    @Column
    @SerializedName("favorites_count")
    int favoritesCount;

    @Column
    @ForeignKey(saveForeignKeyModel = false)
    @SerializedName("flow")
    Flow flow;

    @Column
    @SerializedName("is_habred")
    boolean habred;
    @Column
    @SerializedName("has_polls")
    boolean hasPolls;
    @Column
    @SerializedName("is_interesting")
    boolean interesting;

    @Column
    @ForeignKey(saveForeignKeyModel = false)
    @SerializedName("metadata")
    PostMeta postMeta;

    @Column
    @SerializedName("post_type")
    int postType;
    @Column
    @SerializedName("post_type_str")
    String postTypeStr;
    @Column
    @SerializedName("preview_html")
    String previewHtml;
    @Column
    @SerializedName("reading_count")
    int readingCount;
    @Column
    @SerializedName("is_recovery_mode")
    boolean recoveryMode;
    @Column
    @SerializedName("score")
    int score;
    @Column
    @SerializedName("tags_string")
    String tagsString;
    @Column
    @SerializedName("text_cut")
    String textCut;
    @Column
    @SerializedName("time_favorited")
    Date timeFavorited;
    @Column
    @SerializedName("time_interesting")
    Date timeInteresting;
    @Column
    @SerializedName("time_published")
    Date timePublished;
    @Column
    @SerializedName("title")
    String title;
    @Column
    @SerializedName("is_tutorial")
    boolean tutorial;
    @Column
    @SerializedName("url")
    String url;
    @Column
    @SerializedName("vote")
    int vote;
    @Column
    @SerializedName("votes_count")
    int votesCount;
    @Column
    @SerializedName("text_html")
    String textHtml;

    @SerializedName("hubs")
    List<Hub> hubs;

    @SerializedName("polls")
    List<Poll> polls;

    public User getAuthor() {
        return author;
    }

    public boolean isCanVote() {
        return canVote;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public int getFavoritesCount() {
        return favoritesCount;
    }

    public Flow getFlow() {
        return flow;
    }

    public boolean isHabred() {
        return habred;
    }

    public boolean isHasPolls() {
        return hasPolls;
    }


    public long getId() {
        return id;
    }

    public boolean isInteresting() {
        return interesting;
    }

    public PostMeta getPostMeta() {
        return postMeta;
    }

    public int getPostType() {
        return postType;
    }

    public String getPostTypeStr() {
        return postTypeStr;
    }

    public String getPreviewHtml() {
        return previewHtml;
    }

    public int getReadingCount() {
        return readingCount;
    }

    public boolean isRecoveryMode() {
        return recoveryMode;
    }

    public int getScore() {
        return score;
    }

    public String getTagsString() {
        return tagsString;
    }

    public String getTextCut() {
        return textCut;
    }

    public String getTextHtml() {
        return textHtml;
    }

    public Date getTimeFavorited() {
        return timeFavorited;
    }

    public Date getTimeInteresting() {
        return timeInteresting;
    }

    public Date getTimePublished() {
        return timePublished;
    }

    public String getTitle() {
        return title;
    }

    public boolean isTutorial() {
        return tutorial;
    }

    public String getUrl() {
        return url;
    }

    public int getVote() {
        return vote;
    }

    public int getVotesCount() {
        return votesCount;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "hubs")
    public List<Hub> getHubs() {
        if (hubs == null || hubs.isEmpty()) {
            hubs = SQLite.select()
                    .from(Hub.class)
                    .where(Hub_Table.id.eq(id))
                    .queryList();
        }
        return hubs;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "polls")
    public List<Poll> getPolls() {
        if (polls == null || polls.isEmpty()) {
            polls = SQLite.select()
                    .from(Poll.class)
                    .where(Poll_Table.id.eq(id))
                    .queryList();
        }
        return polls;
    }
}