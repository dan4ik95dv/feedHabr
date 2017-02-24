package name.sportivka.feed.di.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import name.sportivka.feed.di.scope.APostsScope;
import name.sportivka.feed.mvp.presenter.APostsPresenter;

@APostsScope
@Module
public class APostsModule {

    public Context context;

    public APostsModule(Context context) {
        this.context = context;
    }

    @Provides
    APostsPresenter provideAPostsPresenter() {
        return new APostsPresenter(context);
    }


}
