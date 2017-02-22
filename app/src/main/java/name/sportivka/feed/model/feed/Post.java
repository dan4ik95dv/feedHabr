package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by daniil on 23.02.17.
 */


public class Post {
    @SerializedName("author")
    User author;
    @SerializedName("is_can_vote")
    boolean canVote;
    @SerializedName("comments_count")
    int commentsCount;
    @SerializedName("is_favorite")
    boolean favorite;
    @SerializedName("favorites_count")
    int favoritesCount;
    @SerializedName("flow")
    Flow flow;
    @SerializedName("is_habred")
    boolean habred;
    @SerializedName("has_polls")
    boolean hasPolls;
    @SerializedName("hubs")
    List<Hub> hubs;
    @SerializedName("id")
    long id;
    @SerializedName("is_interesting")
    boolean interesting;
    @SerializedName("polls")
    List<Poll> polls;
    @SerializedName("metadata")
    PostMeta postMeta;
    @SerializedName("post_type")
    int postType;
    @SerializedName("post_type_str")
    String postTypeStr;
    @SerializedName("preview_html")
    String previewHtml;
    @SerializedName("reading_count")
    int readingCount;
    @SerializedName("is_recovery_mode")
    boolean recoveryMode;
    @SerializedName("score")
    int score;
    @SerializedName("tags_string")
    String tagsString;
    @SerializedName("text_cut")
    String textCut;
    @SerializedName("time_favorited")
    Date timeFavorited;
    @SerializedName("time_interesting")
    Date timeInteresting;
    @SerializedName("time_published")
    Date timePublished;
    @SerializedName("title")
    String title;
    @SerializedName("is_tutorial")
    boolean tutorial;
    @SerializedName("url")
    String url;
    @SerializedName("vote")
    int vote;
    @SerializedName("votes_count")
    int votesCount;
    @SerializedName("text_html")
    private String textHtml;

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

    public List<Hub> getHubs() {
        return hubs;
    }

    public long getId() {
        return id;
    }

    public boolean isInteresting() {
        return interesting;
    }

    public List<Poll> getPolls() {
        return polls;
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
}