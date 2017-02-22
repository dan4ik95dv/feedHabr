package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniil on 23.02.17.
 */


public class Hub {
    @SerializedName("about")
    String about;
    @SerializedName("alias")
    String alias;
    @SerializedName("is_company")
    boolean company;
    @SerializedName("count_posts")
    int countPosts;
    @SerializedName("count_subscribers")
    int countSubscribers;
    @SerializedName("id")
    long id;
    @SerializedName("is_membership")
    boolean membership;
    @SerializedName("is_profiled")
    boolean profiled;
    @SerializedName("rating")
    float rating;
    @SerializedName("tags_string")
    String tagsString;
    @SerializedName("title")
    String title;

    public String getAbout() {
        return about;
    }

    public String getAlias() {
        return alias;
    }

    public boolean isCompany() {
        return company;
    }

    public int getCountPosts() {
        return countPosts;
    }

    public int getCountSubscribers() {
        return countSubscribers;
    }

    public long getId() {
        return id;
    }

    public boolean isMembership() {
        return membership;
    }

    public boolean isProfiled() {
        return profiled;
    }

    public float getRating() {
        return rating;
    }

    public String getTagsString() {
        return tagsString;
    }

    public String getTitle() {
        return title;
    }
}
