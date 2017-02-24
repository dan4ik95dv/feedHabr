package name.sportivka.feed.di.activity;

import dagger.Component;
import name.sportivka.feed.di.scope.ASplashScope;
import name.sportivka.feed.ui.activity.ASplashActivity;

@ASplashScope
@Component(modules = ASplashModule.class)
public interface ASplashComponent {

    void inject(ASplashActivity activity);

}
