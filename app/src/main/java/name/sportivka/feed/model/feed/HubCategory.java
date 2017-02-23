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
public class HubCategory extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    long myId;
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
