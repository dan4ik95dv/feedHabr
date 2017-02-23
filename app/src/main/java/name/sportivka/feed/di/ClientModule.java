package name.sportivka.feed.di;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import name.sportivka.feed.di.scope.AppScope;
import name.sportivka.feed.network.api.FlowApi;
import name.sportivka.feed.network.api.HubApi;
import name.sportivka.feed.network.api.PostApi;
import retrofit2.Retrofit;

@Module
public class ClientModule {

    public ClientModule() {
    }

    @Provides
    @AppScope
    FlowApi provideFlowApi(Retrofit retrofit) {
        return retrofit.create(FlowApi.class);
    }

    @Provides
    @AppScope
    HubApi provideHubApi(Retrofit retrofit) {
        return retrofit.create(HubApi.class);
    }

    @Provides
    @AppScope
    PostApi providePostApi(Retrofit retrofit) {
        return retrofit.create(PostApi.class);
    }
}
