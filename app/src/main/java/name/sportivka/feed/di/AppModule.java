package name.sportivka.feed.di;

import android.content.Context;

import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;
import name.sportivka.feed.App;
import name.sportivka.feed.di.scope.AppScope;

/**
 * Created by daniil on 23.02.17.
 */
@Module
public class AppModule {
    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @AppScope
    public Context provideContext() {
        return this.app.getApplicationContext();
    }

    @Provides
    @AppScope
    public App provideApp() {
        return this.app;
    }

    @Provides
    @AppScope
    public Bus provideBus() {
        return new Bus();
    }
}
