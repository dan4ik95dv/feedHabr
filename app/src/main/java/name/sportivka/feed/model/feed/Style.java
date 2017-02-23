package name.sportivka.feed.model.feed;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import name.sportivka.feed.model.MyDatabase;

/**
 * Created by daniil on 23.02.17.
 */

@Table(database = MyDatabase.class)
public class Style extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    long myId;
    @Column
    String style;

    public Style() {
    }

    public Style(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }
}
