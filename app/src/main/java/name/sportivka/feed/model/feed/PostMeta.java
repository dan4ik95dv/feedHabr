package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import name.sportivka.feed.model.MyDatabase;

/**
 * Created by daniil on 23.02.17.
 */

@Table(database = MyDatabase.class)
public class PostMeta extends BaseModel {
    @Column
    @PrimaryKey
    long myId;
    @Column
    @SerializedName("edit_url")
    String editUrl;
    @Column
    @SerializedName("img_darken")
    int imgDarken;
    @Column
    @SerializedName("meta_image")
    String metaImage;
    @Column
    @SerializedName("post_image_url")
    String postImageUrl;
    @Column
    @SerializedName("preview_image_url")
    String previewImageUrl;
    @Column
    @SerializedName("title_color")
    String titleColor;
    @Column
    @SerializedName("version")
    String version;

    @SerializedName("scripts")
    List<Script> scripts;

    @SerializedName("styles")
    List<Style> styles;


    public String getEditUrl() {
        return editUrl;
    }

    public int getImgDarken() {
        return imgDarken;
    }

    public String getMetaImage() {
        return metaImage;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public String getPreviewImageUrl() {
        return previewImageUrl;
    }


    public String getTitleColor() {
        return titleColor;
    }

    public String getVersion() {
        return version;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "styles")
    public List<Style> getStyles() {
        if (styles == null || styles.isEmpty()) {
            styles = SQLite.select()
                    .from(Style.class)
                    .where(Style_Table.myId.eq(myId))
                    .queryList();
        }
        return styles;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "scripts")
    public List<Script> getScripts() {
        if (scripts == null || scripts.isEmpty()) {
            scripts = SQLite.select()
                    .from(Script.class)
                    .where(Script_Table.myId.eq(myId))
                    .queryList();
        }
        return scripts;
    }
}
