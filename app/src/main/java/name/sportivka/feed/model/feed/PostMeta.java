package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import name.sportivka.feed.model.MyDatabase;

/**
 * Created by daniil on 23.02.17.
 */

@Table(database = MyDatabase.class)
public class PostMeta extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    long id;
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
    @SerializedName("scripts")
    List<String> scripts;
    @Column
    @SerializedName("styles")
    List<String> styles;
    @Column
    @SerializedName("title_color")
    String titleColor;
    @Column
    @SerializedName("version")
    String version;

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

    public List<String> getScripts() {
        return scripts;
    }

    public List<String> getStyles() {
        return styles;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public String getVersion() {
        return version;
    }
}
