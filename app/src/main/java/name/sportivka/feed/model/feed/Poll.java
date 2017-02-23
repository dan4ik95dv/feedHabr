package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import name.sportivka.feed.model.MyDatabase;

/**
 * Created by daniil on 23.02.17.
 */
@Table(database = MyDatabase.class)
public class Poll extends BaseModel {
    @Column
    @PrimaryKey
    @SerializedName("id")
    private long id;
    @Column
    @SerializedName("percent")
    int percent;
    @Column
    @SerializedName("answers_type")
    private String answersType;
    @Column
    @SerializedName("can_vote")
    private boolean canVote;

    @Column
    @SerializedName("pass_count")
    private int passCount;
    @Column
    @SerializedName("post_id")
    private long postId;
    @Column
    @SerializedName("text")
    private String text;
    @Column
    @SerializedName("text_html")
    private String textHtml;
    @Column
    @SerializedName("time_elapsed")
    private String timeElapsed;
    @Column
    @SerializedName("variants")
    private List<PollVariant> variants;
    @Column
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
