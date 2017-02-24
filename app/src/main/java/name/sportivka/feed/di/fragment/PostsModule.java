package name.sportivka.feed.di.fragment;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import name.sportivka.feed.di.scope.FPostsScope;
import name.sportivka.feed.mvp.presenter.PostsPresenter;

@FPostsScope
@Module
public class PostsModule {

    public Context context;

    public PostsModule(Context context) {
        this.context = context;
    }

    @Provides
    PostsPresenter providePostsPresenter() {
        return new PostsPresenter(context);
    }


}
