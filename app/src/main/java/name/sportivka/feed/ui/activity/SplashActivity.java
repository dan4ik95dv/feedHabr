package name.sportivka.feed.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import name.sportivka.feed.R;
import name.sportivka.feed.di.DaggerSplashComponent;
import name.sportivka.feed.di.SplashModule;
import name.sportivka.feed.mvp.presenter.SplashPresenter;
import name.sportivka.feed.mvp.view.SplashMvpView;

public class SplashActivity extends BaseActivity implements SplashMvpView {
    @Inject
    SplashPresenter presenter;

    @BindView(R.id.fullscreen_content)
    TextView mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerSplashComponent.builder().splashModule(new SplashModule(this)).build().inject(this);
        setContentView(R.layout.activity_splash);
        presenter.init();
    }

    @Override
    public void showSysBar() {
        showSystemBar(mContentView);
    }

    @Override
    public void hideSysBar() {
        hideSystemBar(mContentView);
    }
}
