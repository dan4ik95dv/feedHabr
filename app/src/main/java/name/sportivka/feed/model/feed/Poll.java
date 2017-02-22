package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by daniil on 23.02.17.
 */

public class Poll {
    @SerializedName("percent")
    int percent;
    @SerializedName("answers_type")
    private String answersType;
    @SerializedName("can_vote")
    private boolean canVote;
    @SerializedName("id")
    private long id;
    @SerializedName("pass_count")
    private int passCount;
    @SerializedName("post_id")
    private long postId;
    @SerializedName("text")
    private String text;
    @SerializedName("text_html")
    private String textHtml;
    @SerializedName("time_elapsed")
    private String timeElapsed;
    @SerializedName("variants")
    private List<PollVariant> variants;
    @SerializedName("votes_count")
    private int votesCount;

    public String getAnswersType() {
        return answersType;
    }

    public boolean isCanVote() {
        return canVote;
    }

    public long getId() {
        return id;
    }

    public int getPassCount() {
        return passCount;
    }

    public int getPercent() {
        return percent;
    }

    public long getPostId() {
        return postId;
    }

    public String getText() {
        return text;
    }

    public String getTextHtml() {
        return textHtml;
    }

    public String getTimeElapsed() {
        return timeElapsed;
    }

    public List<PollVariant> getVariants() {
        return variants;
    }

    public int getVotesCount() {
        return votesCount;
    }
}
