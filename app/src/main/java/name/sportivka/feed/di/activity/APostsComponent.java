package name.sportivka.feed.di.activity;

import dagger.Component;
import name.sportivka.feed.di.scope.APostsScope;
import name.sportivka.feed.ui.activity.APostsActivity;

@APostsScope
@Component(modules = APostsModule.class)
public interface APostsComponent {

    void inject(APostsActivity activity);

}
