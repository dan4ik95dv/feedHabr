package name.sportivka.feed.model.feed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniil on 23.02.17.
 */

public class Simple {
    @SerializedName("ok")
    boolean ok;
    @SerializedName("additional")
    private Object additional;
    @SerializedName("message")
    private String message;
    @SerializedName("server_time")
    private String serverTime;

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
