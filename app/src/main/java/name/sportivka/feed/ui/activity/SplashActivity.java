package name.sportivka.feed.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import butterknife.BindView;
import name.sportivka.feed.Constants;
import name.sportivka.feed.R;

public class SplashActivity extends BaseActivity {

    private final Handler mHideHandler = new Handler();
    @BindView(R.id.fullscreen_content)
    TextView mContentView;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            showSystemBar(mContentView);
            startMainAcitity();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        hideSystemBar(mContentView);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(Constants.DELAY);
    }


    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
