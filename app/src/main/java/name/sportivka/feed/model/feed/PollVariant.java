package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniil on 23.02.17.
 */

public class PollVariant {
    private boolean firstPlace;
    @SerializedName("id")
    private long id;
    @SerializedName("percent")
    private int percent;
    @SerializedName("polling_question_id")
    private long pollingQuestionId;
    @SerializedName("post_id")
    private long postId;
    @SerializedName("text")
    private String text;
    @SerializedName("text_html")
    private String textHtml;
    @SerializedName("votes_count")
    private int votesCount;

    public boolean isFirstPlace() {
        return firstPlace;
    }

    public long getId() {
        return id;
    }

    public int getPercent() {
        return percent;
    }

    public long getPollingQuestionId() {
        return pollingQuestionId;
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

    public int getVotesCount() {
        return votesCount;
    }
}
