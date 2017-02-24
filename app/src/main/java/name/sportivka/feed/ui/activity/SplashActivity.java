package name.sportivka.feed.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import name.sportivka.feed.R;
import name.sportivka.feed.di.activity.DaggerSplashComponent;
import name.sportivka.feed.di.activity.SplashModule;
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
        presenter.attachView(this);
        setContentView(R.layout.activity_splash);
        presenter.init();
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
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
