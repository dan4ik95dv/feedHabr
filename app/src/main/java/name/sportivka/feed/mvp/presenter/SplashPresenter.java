package name.sportivka.feed.mvp.presenter;

import android.content.Context;
import android.os.Handler;

import name.sportivka.feed.App;
import name.sportivka.feed.Constants;
import name.sportivka.feed.mvp.Presenter;
import name.sportivka.feed.mvp.view.SplashMvpView;
import name.sportivka.feed.ui.activity.BaseActivity;

/**
 * Created by daniil on 23.02.17.
 */

public class SplashPresenter implements Presenter<SplashMvpView> {

    private SplashMvpView splashMvpView;
    private Context context;
    private BaseActivity activity;
    private Handler mHideHandler = new Handler();
    private Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            if (splashMvpView != null)
                splashMvpView.hideSysBar();
            activity.startMainAcitity();
        }
    };

    public SplashPresenter(Context context) {
        inject(context);
        this.context = context;
        this.activity = (BaseActivity) context;
    }

    @Override
    public void attachView(SplashMvpView view) {
        this.splashMvpView = view;
    }

    @Override
    public void detachView() {
        this.splashMvpView = null;
    }


    protected void inject(Context context) {
        ((App) context.getApplicationContext()).appComponent().inject(this);
    }

    public void init() {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, Constants.DELAY);
    }
}
