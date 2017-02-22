package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniil on 23.02.17.
 */


public class Geo {
    @SerializedName("city")
    String city;
    @SerializedName("country")
    String country;
    @SerializedName("region")
    String region;

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }
}
