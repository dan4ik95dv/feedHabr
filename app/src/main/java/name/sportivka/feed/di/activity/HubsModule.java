package name.sportivka.feed.di.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import name.sportivka.feed.di.scope.HubsScope;
import name.sportivka.feed.mvp.presenter.HubsPresenter;

@HubsScope
@Module
public class HubsModule {

    public Context context;

    public HubsModule(Context context) {
        this.context = context;
    }

    @Provides
    HubsPresenter provideHubsPresenter() {
        return new HubsPresenter(context);
    }


}
