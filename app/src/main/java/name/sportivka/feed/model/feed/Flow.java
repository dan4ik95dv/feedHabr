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
public class Flow extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    long id;
    @Column
    @SerializedName("alias")
    String alias;
    @Column
    @SerializedName("hubs_count")
    int hubsCount;
    @Column
    @SerializedName("name")
    String name;
    @Column
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
