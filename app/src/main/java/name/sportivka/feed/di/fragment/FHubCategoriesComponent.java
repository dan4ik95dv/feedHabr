package name.sportivka.feed.di.fragment;

import dagger.Component;
import name.sportivka.feed.di.scope.FPostsScope;
import name.sportivka.feed.ui.fragment.FHubCategoriesFragment;

@FPostsScope
@Component(modules = FHubCategoriesModule.class)
public interface FHubCategoriesComponent {

    void inject(FHubCategoriesFragment fragment);

}
