package name.sportivka.feed.di.activity;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import name.sportivka.feed.di.scope.AMainScope;
import name.sportivka.feed.mvp.presenter.AMainPresenter;

@AMainScope
@Module
public class AMainModule {

    public Context context;

    public AMainModule(Context context) {
        this.context = context;
    }

    @Provides
    AMainPresenter provideMainPresenter() {
        return new AMainPresenter(context);
    }


}
