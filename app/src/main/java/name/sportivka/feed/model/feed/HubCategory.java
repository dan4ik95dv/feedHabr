package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniil on 23.02.17.
 */


public class HubCategory {
    @SerializedName("alias")
    String alias;
    @SerializedName("count")
    int hubsCount;
    @SerializedName("title")
    String title;
    @SerializedName("url")
    String url;

    public String getAlias() {
        return alias;
    }

    public int getHubsCount() {
        return hubsCount;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
