package name.sportivka.feed.network.api;

import java.util.List;
import java.util.Map;

import name.sportivka.feed.model.Response;
import name.sportivka.feed.model.feed.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by daniil on 22.02.17.
 */

public interface PostApi {

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




}