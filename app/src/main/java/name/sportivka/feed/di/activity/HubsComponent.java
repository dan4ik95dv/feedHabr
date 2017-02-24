package name.sportivka.feed.di.activity;

import dagger.Component;
import name.sportivka.feed.di.scope.HubsScope;
import name.sportivka.feed.ui.activity.HubsActivity;

@HubsScope
@Component(modules = HubsModule.class)
public interface HubsComponent {

    void inject(HubsActivity activity);

}
