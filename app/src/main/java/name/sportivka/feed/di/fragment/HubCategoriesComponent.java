package name.sportivka.feed.di.fragment;

import dagger.Component;
import name.sportivka.feed.di.scope.FPostsScope;
import name.sportivka.feed.ui.fragment.HubCategoriesFragment;

@FPostsScope
@Component(modules = HubCategoriesModule.class)
public interface HubCategoriesComponent {

    void inject(HubCategoriesFragment fragment);

}
