package name.sportivka.feed.network.api;

import java.util.List;

import name.sportivka.feed.model.Response;
import name.sportivka.feed.model.feed.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by daniil on 22.02.17.
 */

public interface PostApi {

    @GET("post/{post}")
    Call<Response<Post>> getPost(@Path("post") long post, @Query("get_article") boolean getArticle);

    @GET("posts/all")
    Call<Response<List<Post>>> getPubAll(@Query("page") int page, @Query("include") String include, @Query("exclude") String exclude, @Query("limit") int limit);

    @GET("top/{type}")
    Call<Response<List<Post>>> getPubBest(@Path("type") String type, @Query("page") int page, @Query("include") String include, @Query("exclude") String exclude, @Query("limit") int limit);

    @GET("posts/interesting")
    Call<Response<List<Post>>> getPubInteresting(@Query("page") int page, @Query("include") String include, @Query("exclude") String exclude, @Query("limit") int limit);

    @GET("feed/all")
    Call<Response<List<Post>>> getPubSubscription(@Query("page") int page, @Query("include") String include, @Query("exclude") String exclude, @Query("limit") int limit);

    @GET("search/posts/{search}")
    Call<Response<List<Post>>> searchPosts(@Path("search") String search, @Query("page") int page, @Query("sort") String sort, @Query("get_article") boolean getArticle);


}