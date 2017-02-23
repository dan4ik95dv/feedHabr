package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by daniil on 23.02.17.
 */


public class HubCategory extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    long id;
    @Column
    @SerializedName("alias")
    String alias;
    @Column
    @SerializedName("count")
    int hubsCount;
    @Column
    @SerializedName("title")
    String title;
    @Column
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
