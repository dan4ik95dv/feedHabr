package name.sportivka.feed.di;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Component;
import name.sportivka.feed.App;
import name.sportivka.feed.di.scope.AppScope;
import name.sportivka.feed.mvp.presenter.HubCategoriesPresenter;
import name.sportivka.feed.mvp.presenter.HubsPresenter;
import name.sportivka.feed.mvp.presenter.MainPresenter;
import name.sportivka.feed.mvp.presenter.PostsPresenter;
import name.sportivka.feed.mvp.presenter.SplashPresenter;

/**
 * Created by daniil on 23.02.17.
 */

@Component(modules = {AppModule.class, NetModule.class, ClientModule.class, StorageModule.class})
@AppScope
public interface AppComponent {

    Context getContext();

    SharedPreferences getSharedPreferences();

    void inject(App app);

    void inject(SplashPresenter presenter);

    void inject(MainPresenter presenter);

    void inject(PostsPresenter presenter);

    void inject(HubCategoriesPresenter presenter);

    void inject(HubsPresenter presenter);
}
