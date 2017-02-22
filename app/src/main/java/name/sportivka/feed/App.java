package name.sportivka.feed;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by daniil on 23.02.17.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Stetho.initialize(Stetho.newInitializerBuilder(this).build());
        }
    }
}
