package name.sportivka.feed.network.api;

import java.util.List;

import name.sportivka.feed.model.Response;
import name.sportivka.feed.model.feed.Flow;
import name.sportivka.feed.model.feed.Hub;
import name.sportivka.feed.model.feed.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by daniil on 23.02.17.
 */

public interface FlowApi {
    @GET("flows/{flow}/{type}")
    Call<Response<List<Post>>> getFlowHubFeed(@Path("flow") String flow, @Path("type") String type, @Query("page") int page, @Query("include") String include, @Query("exclude") String exclude, @Query("limit") int limit);

    @GET("flows/{flow}/hubs")
    Call<Response<List<Hub>>> getFlowHubs(@Path("flow") String flow, @Query("page") int page);

    @GET("flows")
    Call<Response<List<Flow>>> getFlows();
}
