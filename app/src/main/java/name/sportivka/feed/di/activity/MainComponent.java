package name.sportivka.feed.di.activity;

import dagger.Component;
import name.sportivka.feed.di.scope.MainScope;
import name.sportivka.feed.ui.activity.MainActivity;

@MainScope
@Component(modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity activity);

}
