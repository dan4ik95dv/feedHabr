package name.sportivka.feed.di.activity;

import dagger.Component;
import name.sportivka.feed.di.scope.AMainScope;
import name.sportivka.feed.ui.activity.AMainActivity;

@AMainScope
@Component(modules = AMainModule.class)
public interface AMainComponent {

    void inject(AMainActivity activity);

}
