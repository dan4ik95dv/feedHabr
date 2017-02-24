package name.sportivka.feed.di.fragment;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import name.sportivka.feed.di.scope.FHubCategoriesScope;
import name.sportivka.feed.mvp.presenter.HubCategoriesPresenter;

@FHubCategoriesScope
@Module
public class HubCategoriesModule {

    public Context context;

    public HubCategoriesModule(Context context) {
        this.context = context;
    }

    @Provides
    HubCategoriesPresenter provideHubCategoriesPresenter() {
        return new HubCategoriesPresenter(context);
    }


}
