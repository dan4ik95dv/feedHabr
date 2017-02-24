package name.sportivka.feed.di.fragment;

import dagger.Component;
import name.sportivka.feed.di.scope.FPostsScope;
import name.sportivka.feed.ui.fragment.FPostsFragment;

@FPostsScope
@Component(modules = FPostsModule.class)
public interface FPostsComponent {

    void inject(FPostsFragment fragment);

}
