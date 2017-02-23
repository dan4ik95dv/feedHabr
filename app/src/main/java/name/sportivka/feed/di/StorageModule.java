package name.sportivka.feed.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = AppModule.class)
public class StorageModule {

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
