package name.sportivka.feed.di.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import name.sportivka.feed.di.scope.APostScope;
import name.sportivka.feed.mvp.presenter.APostPresenter;

@APostScope
@Module
public class APostModule {

    public Context context;

    public APostModule(Context context) {
        this.context = context;
    }

    @Provides
    APostPresenter provideAPostsPresenter() {
        return new APostPresenter(context);
    }


}
