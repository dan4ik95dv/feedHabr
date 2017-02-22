package name.sportivka.feed.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniil on 23.02.17.
 */

public class NextPage {
    @SerializedName("int")
    private int next;
    @SerializedName("url")
    private String url;

    public int getNext() {
        return next;
    }

    public String getUrl() {
        return url;
    }
}