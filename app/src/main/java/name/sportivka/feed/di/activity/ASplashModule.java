package name.sportivka.feed.di.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import name.sportivka.feed.di.scope.ASplashScope;
import name.sportivka.feed.mvp.presenter.ASplashPresenter;

@ASplashScope
@Module
public class ASplashModule {
    public Context context;

    public ASplashModule(Context context) {
        this.context = context;
    }

    @Provides
    ASplashPresenter provideSplashPresenter() {
        return new ASplashPresenter(context);
    }
}
