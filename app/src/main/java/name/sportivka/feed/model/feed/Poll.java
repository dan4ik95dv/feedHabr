package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.sql.language.SQLite;
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
    @Unique
    @SerializedName("id")
    long myId;
    @Column
    @SerializedName("percent")
    int percent;
    @Column
    @SerializedName("answers_type")
    String answersType;
    @Column
    @SerializedName("can_vote")
    boolean canVote;

    @Column
    @SerializedName("pass_count")
    int passCount;
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
    @SerializedName("time_elapsed")
    String timeElapsed;

    @Column
    @SerializedName("votes_count")
    int votesCount;

    @SerializedName("variants")
    List<PollVariant> variants;

    public String getAnswersType() {
        return answersType;
    }

    public boolean isCanVote() {
        return canVote;
    }

    public long getId() {
        return myId;
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

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "variants")
    public List<PollVariant> getVariants() {
        if (variants == null || variants.isEmpty()) {
            variants = SQLite.select()
                    .from(PollVariant.class)
                    .where(PollVariant_Table.myId.eq(myId))
                    .queryList();
        }
        return variants;
    }

    public int getVotesCount() {
        return votesCount;
    }
}
