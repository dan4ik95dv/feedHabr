package name.sportivka.feed.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import name.sportivka.feed.R;
import name.sportivka.feed.di.activity.ASplashModule;
import name.sportivka.feed.di.activity.DaggerASplashComponent;
import name.sportivka.feed.mvp.presenter.ASplashPresenter;
import name.sportivka.feed.mvp.view.ASplashMvpView;

public class ASplashActivity extends BaseActivity implements ASplashMvpView {
    @Inject
    ASplashPresenter presenter;

    @BindView(R.id.fullscreen_content)
    TextView mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerASplashComponent.builder().aSplashModule(new ASplashModule(this)).build().inject(this);
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
