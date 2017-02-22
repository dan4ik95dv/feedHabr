package name.sportivka.feed.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by daniil on 23.02.17.
 */

public class Response<T> {
    @SerializedName("next_link")
    NextPage nextLink;
    @SerializedName("next_page")
    NextPage nextPage;
    @SerializedName("pages")
    String pageCount;
    @SerializedName("server_time")
    Date serverTime;
    @SerializedName("sorted_by")
    String sortedBy;
    @SerializedName("data")
    private T data;

    public T getData() {
        return data;
    }

    public NextPage getNextLink() {
        return nextLink;
    }

    public NextPage getNextPage() {
        return nextPage;
    }

    public String getPageCount() {
        return pageCount;
    }

    public Date getServerTime() {
        return serverTime;
    }

    public String getSortedBy() {
        return sortedBy;
    }
}
