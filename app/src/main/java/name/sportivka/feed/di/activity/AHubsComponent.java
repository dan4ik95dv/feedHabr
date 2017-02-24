package name.sportivka.feed.di.activity;

import dagger.Component;
import name.sportivka.feed.di.scope.AHubsScope;
import name.sportivka.feed.ui.activity.AHubsActivity;

@AHubsScope
@Component(modules = AHubsModule.class)
public interface AHubsComponent {

    void inject(AHubsActivity activity);

}
