package name.sportivka.feed.di.fragment;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import name.sportivka.feed.di.scope.FHubCategoriesScope;
import name.sportivka.feed.mvp.presenter.FHubCategoriesPresenter;

@FHubCategoriesScope
@Module
public class FHubCategoriesModule {

    public Context context;

    public FHubCategoriesModule(Context context) {
        this.context = context;
    }

    @Provides
    FHubCategoriesPresenter provideHubCategoriesPresenter() {
        return new FHubCategoriesPresenter(context);
    }


}
