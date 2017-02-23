package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniil on 23.02.17.
 */

public class Simple {
    @SerializedName("ok")
    boolean ok;
    @SerializedName("additional")
    Object additional;
    @SerializedName("message")
    String message;
    @SerializedName("server_time")
    String serverTime;

    public Object getAdditional() {
        return additional;
    }

    public String getMessage() {
        return message;
    }

    public boolean isOk() {
        return ok;
    }

    public String getServerTime() {
        return serverTime;
    }
}
