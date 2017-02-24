package name.sportivka.feed.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.squareup.otto.Bus;

import dagger.Component;
import name.sportivka.feed.App;
import name.sportivka.feed.di.scope.AppScope;
import name.sportivka.feed.mvp.presenter.AHubsPresenter;
import name.sportivka.feed.mvp.presenter.AMainPresenter;
import name.sportivka.feed.mvp.presenter.APostPresenter;
import name.sportivka.feed.mvp.presenter.APostsPresenter;
import name.sportivka.feed.mvp.presenter.ASplashPresenter;
import name.sportivka.feed.mvp.presenter.FHubCategoriesPresenter;
import name.sportivka.feed.mvp.presenter.FPostsPresenter;

/**
 * Created by daniil on 23.02.17.
 */

@Component(modules = {AppModule.class, NetModule.class, ClientModule.class, StorageModule.class})
@AppScope
public interface AppComponent {

    Context getContext();

    SharedPreferences getSharedPreferences();

    Bus getBus();

    void inject(App app);

    void inject(ASplashPresenter presenter);

    void inject(AMainPresenter presenter);

    void inject(FPostsPresenter presenter);

    void inject(FHubCategoriesPresenter presenter);

    void inject(AHubsPresenter presenter);

    void inject(APostsPresenter presenter);

    void inject(APostPresenter presenter);
}
