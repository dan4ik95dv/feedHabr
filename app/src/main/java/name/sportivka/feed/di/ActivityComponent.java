package name.sportivka.feed.di;

import name.sportivka.feed.ui.activity.BaseActivity;
import name.sportivka.feed.ui.activity.MainActivity;
import name.sportivka.feed.ui.activity.SplashActivity;

/**
 * Created by daniil on 23.02.17.
 */

public interface ActivityComponent {
    void inject(BaseActivity baseActivity);

    void inject(MainActivity mainActivity);

    void inject(SplashActivity splashActivity);
}
