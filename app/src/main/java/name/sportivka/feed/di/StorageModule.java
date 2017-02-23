package name.sportivka.feed.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Module;
import dagger.Provides;
import name.sportivka.feed.di.scope.AppScope;

@Module(includes = AppModule.class)
public class StorageModule {

    @Provides
    @AppScope
    SharedPreferences providesSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
