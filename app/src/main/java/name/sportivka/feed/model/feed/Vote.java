package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniil on 23.02.17.
 */

public class Vote extends Simple {
    @SerializedName("score")
    private int score;

    public int getScore() {
        return score;
    }
}
