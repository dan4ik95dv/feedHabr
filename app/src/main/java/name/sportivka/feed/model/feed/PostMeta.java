package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by daniil on 23.02.17.
 */

public class PostMeta {
    @SerializedName("edit_url")
    String editUrl;
    @SerializedName("img_darken")
    int imgDarken;
    @SerializedName("meta_image")
    String metaImage;
    @SerializedName("post_image_url")
    String postImageUrl;
    @SerializedName("preview_image_url")
    String previewImageUrl;
    @SerializedName("scripts")
    List<String> scripts;
    @SerializedName("styles")
    List<String> styles;
    @SerializedName("title_color")
    String titleColor;
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
