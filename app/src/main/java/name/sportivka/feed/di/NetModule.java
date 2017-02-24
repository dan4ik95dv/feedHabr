package name.sportivka.feed.di;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import name.sportivka.feed.BuildConfig;
import name.sportivka.feed.Constants;
import name.sportivka.feed.di.scope.AppScope;
import name.sportivka.feed.model.feed.Script;
import name.sportivka.feed.model.feed.Style;
import name.sportivka.feed.network.ConnectionDetector;
import name.sportivka.feed.network.DBFlowExclusionStrategy;
import name.sportivka.feed.network.MainIntercepter;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    public NetModule() {
    }

    @Provides
    @AppScope
    ConnectionDetector provideConnectionDetector(Context context) {
        return new ConnectionDetector(context);
    }

    @Provides
    @AppScope
    MainIntercepter provideMainIntercepter() {
        return new MainIntercepter();
    }

    @Provides
    @AppScope
    Gson provideGson() {
        Type scriptString = new TypeToken<List<Script>>() {
        }.getType();
        Type styleString = new TypeToken<List<Style>>() {
        }.getType();
        return new GsonBuilder()
                .registerTypeAdapter(scriptString, new TypeAdapter<List<Script>>() {
                    @Override
                    public void write(JsonWriter out, List<Script> value) throws IOException {
                        // Ignore
                    }

                    @Override
                    public List<Script> read(JsonReader in) throws IOException {
                        List<Script> list = new ArrayList<>();
                        in.beginArray();
                        while (in.hasNext()) {
                            list.add(new Script(in.nextString()));
                        }
                        in.endArray();
                        return list;
                    }
                })
                .registerTypeAdapter(styleString, new TypeAdapter<List<Style>>() {
                    @Override
                    public void write(JsonWriter out, List<Style> value) throws IOException {
                        // Ignore
                    }

                    @Override
                    public List<Style> read(JsonReader in) throws IOException {
                        List<Style> list = new ArrayList<>();
                        in.beginArray();
                        while (in.hasNext()) {
                            list.add(new Style(in.nextString()));
                        }
                        in.endArray();
                        return list;
                    }
                })
                .setExclusionStrategies(new DBFlowExclusionStrategy())
                .create();
    }

    @Provides
    @AppScope
    X509TrustManager provideX509TrustManager() {
        return new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    @Provides
    @AppScope
    SSLSocketFactory provideSSLSocketFactory(X509TrustManager x509TrustManager) {
        SSLSocketFactory sslSocketFactory = null;
        try {

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{x509TrustManager}, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e2) {
            e2.printStackTrace();
        }
        return sslSocketFactory;
    }

    @Provides
    @AppScope
    OkHttpClient provideOkHttpClient(MainIntercepter mainIntercepter, SSLSocketFactory sslSocketFactory, X509TrustManager x509TrustManager) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .addInterceptor(mainIntercepter)
                .retryOnConnectionFailure(true)
//                .addNetworkInterceptor(new StethoInterceptor())
                .sslSocketFactory(sslSocketFactory, x509TrustManager)
                .connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS);

        return builder.build();
    }


    @Provides
    @AppScope
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.DEBUG ? Constants.API_URL_DEV : Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }


}
