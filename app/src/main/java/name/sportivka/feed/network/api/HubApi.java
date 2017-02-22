package name.sportivka.feed.network.api;

import java.util.List;
import java.util.Map;

import name.sportivka.feed.model.Response;
import name.sportivka.feed.model.feed.Hub;
import name.sportivka.feed.model.feed.HubCategory;
import name.sportivka.feed.model.feed.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by daniil on 23.02.17.
 */

public interface HubApi {
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
