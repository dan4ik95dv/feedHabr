package name.sportivka.feed;

import android.app.Application;

import com.facebook.stetho.Stetho;

import name.sportivka.feed.di.AppComponent;
import name.sportivka.feed.di.AppModule;
import name.sportivka.feed.di.DaggerAppComponent;

/**
 * Created by daniil on 23.02.17.
 */

public class App extends Application {
    AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Stetho.initialize(Stetho.newInitializerBuilder(this).build());
        }
        initAppComponent();
    }

    public AppComponent appComponent() {
        return this.appComponent;
    }

    private void initAppComponent() {
        this.appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }
}
