package name.sportivka.feed.api;

import java.util.List;
import java.util.Map;

import name.sportivka.feed.model.Response;
import name.sportivka.feed.model.feed.Flow;
import name.sportivka.feed.model.feed.Hub;
import name.sportivka.feed.model.feed.HubCategory;
import name.sportivka.feed.model.feed.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by daniil on 22.02.17.
 */

public interface Api {

    public static final String QUERY_EXCLUDE = "exclude";
    public static final String QUERY_GET_ARTICLE = "get_article";
    public static final String QUERY_INCLUDE = "include";
    public static final String QUERY_LIMIT = "limit";

    @GET("post/{post}")
    Call<Response<Post>> getPost(@Path("post") long post, @Query("get_article") boolean getArticle);

    @GET("posts/all")
    Call<Response<List<Post>>> getPubAll(@Query("page") int page, @QueryMap Map<String, String> map);

    @GET("top/{type}")
    Call<Response<List<Post>>> getPubBest(@Path("type") String type, @Query("page") int page, @QueryMap Map<String, String> map);

    @GET("posts/interesting")
    Call<Response<List<Post>>> getPubInteresting(@Query("page") int page, @QueryMap Map<String, String> map);

    @GET("feed/all")
    Call<Response<List<Post>>> getPubSubscription(@Query("page") int page, @QueryMap Map<String, String> map);

    @GET("search/posts/{search}")
    Call<Response<List<Post>>> searchPosts(@Path("search") String search, @Query("page") int page, @Query("sort") String sort, @Query("get_article") boolean getArticle);

    @GET("flows/{flow}/{type}")
    Call<Response<List<Post>>> getFlowHubFeed(@Path("flow") String flow, @Path("type") String type, @Query("page") int page, @QueryMap Map<String, String> map);

    @GET("flows/{flow}/hubs")
    Call<Response<List<Hub>>> getFlowHubs(@Path("flow") String flow, @Query("page") int page);

    @GET("flows")
    Call<Response<List<Flow>>> getFlows();

    @GET("hubs")
    Call<Response<List<Hub>>> getAllHubs(@Query("page") int page);

    @GET("hubs/categories")
    Call<Response<List<HubCategory>>> getHubCategroies();

    @GET("hub/{hub}/{type}")
    Call<Response<List<Post>>> getHubFeed(@Path("hub") String hub, @Path("type") String type, @Query("page") int page, @QueryMap Map<String, String> map);

    @GET("hub/{hub}/info")
    Call<Response<Hub>> getHubInfo(@Path("hub") String hub);

    @GET("hubs/categories/{category}")
    Call<Response<List<Hub>>> getHubsForCategory(@Path("category") String category, @Query("page") int page);

}