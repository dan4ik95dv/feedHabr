package name.sportivka.feed.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import name.sportivka.feed.di.scope.SplashScope;
import name.sportivka.feed.mvp.presenter.SplashPresenter;

@SplashScope
@Module
public class SplashModule {
    public Context context;

    public SplashModule(Context context) {
        this.context = context;
    }

    @Provides
    SplashPresenter provideSplashPresenter() {
        return new SplashPresenter(context);
    }
}
