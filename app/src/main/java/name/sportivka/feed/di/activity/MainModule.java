package name.sportivka.feed.di.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import name.sportivka.feed.di.scope.MainScope;
import name.sportivka.feed.mvp.presenter.MainPresenter;

@MainScope
@Module
public class MainModule {

    public Context context;

    public MainModule(Context context) {
        this.context = context;
    }

    @Provides
    MainPresenter provideMainPresenter() {
        return new MainPresenter(context);
    }


}
