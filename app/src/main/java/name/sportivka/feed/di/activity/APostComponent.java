package name.sportivka.feed.di.activity;

import dagger.Component;
import name.sportivka.feed.di.scope.APostScope;
import name.sportivka.feed.ui.activity.APostActivity;

@APostScope
@Component(modules = APostModule.class)
public interface APostComponent {

    void inject(APostActivity activity);

}
