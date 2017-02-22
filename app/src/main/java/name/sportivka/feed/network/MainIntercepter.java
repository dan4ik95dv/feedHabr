package name.sportivka.feed.network;

import java.io.IOException;

import name.sportivka.feed.BuildConfig;
import name.sportivka.feed.Constants;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by daniil on 22.02.17.
 */

public class MainIntercepter implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originRequest = chain.request();
        Request.Builder builder = originRequest.newBuilder();
        builder.addHeader("User-Agent", Constants.USER_AGENT);
        builder.addHeader("apikey", BuildConfig.API_KEY);
        return chain.proceed(builder.build());
    }
}