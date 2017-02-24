package name.sportivka.feed.di.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import name.sportivka.feed.di.scope.AHubsScope;
import name.sportivka.feed.mvp.presenter.AHubsPresenter;

@AHubsScope
@Module
public class AHubsModule {

    public Context context;

    public AHubsModule(Context context) {
        this.context = context;
    }

    @Provides
    AHubsPresenter provideHubsPresenter() {
        return new AHubsPresenter(context);
    }


}
