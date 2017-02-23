package name.sportivka.feed.di;

import dagger.Component;
import name.sportivka.feed.di.scope.SplashScope;
import name.sportivka.feed.ui.activity.SplashActivity;

@SplashScope
@Component(modules = SplashModule.class)
public interface SplashComponent {

    void inject(SplashActivity activity);

}
