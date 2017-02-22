package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniil on 23.02.17.
 */


public class CompanyInfo {
    @SerializedName("name")
    String name;

    public String getName() {
        return name;
    }
}
