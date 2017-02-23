package name.sportivka.feed.di;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Component;
import name.sportivka.feed.App;
import name.sportivka.feed.di.scope.AppScope;
import name.sportivka.feed.provider.HubProvider;

/**
 * Created by daniil on 23.02.17.
 */

@Component(modules = {AppModule.class, NetModule.class, ClientModule.class, StorageModule.class})
@AppScope
public interface AppComponent extends ActivityComponent {

    Context getContext();

    SharedPreferences getSharedPreferences();

    HubProvider getHubProvider();

    void inject(App app);
}
