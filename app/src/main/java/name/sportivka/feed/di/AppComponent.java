package name.sportivka.feed.di;

import android.content.Context;

import dagger.Component;
import name.sportivka.feed.App;
import name.sportivka.feed.di.scope.AppScope;
import name.sportivka.feed.network.api.FlowApi;
import name.sportivka.feed.network.api.HubApi;
import name.sportivka.feed.network.api.PostApi;

/**
 * Created by daniil on 23.02.17.
 */

@Component(modules = {AppModule.class, NetModule.class, ClientModule.class})
@AppScope
public interface AppComponent extends ActivityComponent {

    Context getContext();

    FlowApi getFlowApi();

    HubApi getHubApi();

    PostApi getPostApi();

    void inject(App app);
}
