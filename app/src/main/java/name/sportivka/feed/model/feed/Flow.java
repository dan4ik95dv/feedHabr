package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniil on 23.02.17.
 */


public class Flow {
    @SerializedName("alias")
    String alias;
    @SerializedName("hubs_count")
    int hubsCount;
    @SerializedName("name")
    String name;
    @SerializedName("url")
    String url;

    public String getAlias() {
        return alias;
    }

    public int getHubsCount() {
        return hubsCount;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
