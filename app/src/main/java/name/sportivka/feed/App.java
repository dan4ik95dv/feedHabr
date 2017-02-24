package name.sportivka.feed;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import name.sportivka.feed.di.AppComponent;
import name.sportivka.feed.di.AppModule;
import name.sportivka.feed.di.DaggerAppComponent;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by daniil on 23.02.17.
 */

public class App extends Application {
    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this);

        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        initAppComponent();
    }

    public AppComponent appComponent() {
        return this.appComponent;
    }

    private void initAppComponent() {
        this.appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }
}
