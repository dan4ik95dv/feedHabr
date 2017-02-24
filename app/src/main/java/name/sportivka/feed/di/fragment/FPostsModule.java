package name.sportivka.feed.di.fragment;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import name.sportivka.feed.di.scope.FPostsScope;
import name.sportivka.feed.mvp.presenter.FPostsPresenter;

@FPostsScope
@Module
public class FPostsModule {

    public Context context;

    public FPostsModule(Context context) {
        this.context = context;
    }

    @Provides
    FPostsPresenter providePostsPresenter() {
        return new FPostsPresenter(context);
    }


}
