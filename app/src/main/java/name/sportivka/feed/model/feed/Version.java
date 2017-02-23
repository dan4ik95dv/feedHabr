package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniil on 23.02.17.
 */

public class Version extends Simple {
    @SerializedName("version")
    float version;

    public float getVersion() {
        return version;
    }
}
