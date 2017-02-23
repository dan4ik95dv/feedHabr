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
public class PollVariant extends BaseModel {
    boolean firstPlace;
    @Column
    @PrimaryKey
    @SerializedName("id")
    long id;
    @Column
    @SerializedName("percent")
    int percent;
    @Column
    @SerializedName("polling_question_id")
    long pollingQuestionId;
    @Column
    @SerializedName("post_id")
    long postId;
    @Column
    @SerializedName("text")
    String text;
    @Column
    @SerializedName("text_html")
    String textHtml;
    @Column
    @SerializedName("votes_count")
    int votesCount;

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
