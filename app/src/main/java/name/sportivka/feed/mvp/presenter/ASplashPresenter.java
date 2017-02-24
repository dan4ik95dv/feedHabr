package name.sportivka.feed.mvp.presenter;

import android.content.Context;
import android.os.Handler;

import name.sportivka.feed.App;
import name.sportivka.feed.Constants;
import name.sportivka.feed.mvp.Presenter;
import name.sportivka.feed.mvp.view.ASplashMvpView;
import name.sportivka.feed.ui.activity.BaseActivity;

/**
 * Created by daniil on 23.02.17.
 */

public class ASplashPresenter implements Presenter<ASplashMvpView> {

    private ASplashMvpView ASplashMvpView;
    private Context context;
    private BaseActivity activity;
    private Handler mHideHandler = new Handler();
    private Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            if (ASplashMvpView != null)
                ASplashMvpView.hideSysBar();
            activity.startMainAcitity();
        }
    };

    public ASplashPresenter(Context context) {
        inject(context);
        this.context = context;
        this.activity = (BaseActivity) context;
    }

    @Override
    public void attachView(ASplashMvpView view) {
        this.ASplashMvpView = view;
    }

    @Override
    public void detachView() {
        this.ASplashMvpView = null;
    }


    protected void inject(Context context) {
        ((App) context.getApplicationContext()).appComponent().inject(this);
    }

    public void init() {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, Constants.DELAY);
    }
}
