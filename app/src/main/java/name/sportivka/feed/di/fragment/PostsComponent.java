package name.sportivka.feed.di.fragment;

import dagger.Component;
import name.sportivka.feed.di.scope.FPostsScope;
import name.sportivka.feed.ui.fragment.PostsFragment;

@FPostsScope
@Component(modules = PostsModule.class)
public interface PostsComponent {

    void inject(PostsFragment fragment);

}
