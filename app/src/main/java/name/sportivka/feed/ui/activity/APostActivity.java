package name.sportivka.feed.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import name.sportivka.feed.Constants;
import name.sportivka.feed.R;
import name.sportivka.feed.di.activity.APostModule;
import name.sportivka.feed.di.activity.DaggerAPostComponent;
import name.sportivka.feed.model.feed.Post;
import name.sportivka.feed.mvp.presenter.APostPresenter;
import name.sportivka.feed.mvp.view.APostMvpView;

public class APostActivity extends BaseActivity implements APostMvpView {

    @Inject
    APostPresenter presenter;

    @BindView(R.id.post_webview)
    WebView postWebview;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        DaggerAPostComponent.builder().aPostModule(new APostModule(this)).build().inject(this);
        initToolbar();
        presenter.attachView(this);
        presenter.init();
        initViews();
        initWebView();
    }

    private void initViews() {

        swipeRefreshLayout.setOnRefreshListener(presenter.getRefreshListener());
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark);
    }

    protected void configureLayerType(WebView webView) {
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
    }

    private void enableWVCache(WebView webView) {

        webView.getSettings().setDomStorageEnabled(true);
        File dir = getCacheDir();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        webView.getSettings().setAppCachePath(dir.getPath());
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
    }

    public void initWebView() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2)
            configureLayerType(this.postWebview);

        enableWVCache(this.postWebview);
        this.postWebview.setPadding(8, 8, 8, 8);
        this.postWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.postWebview.setBackgroundColor(0);
        this.postWebview.getSettings().setBuiltInZoomControls(true);
        this.postWebview.getSettings().setDisplayZoomControls(false);
        this.postWebview.reload();
    }

    public void initToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showPost(Post data) {
        postWebview.loadDataWithBaseURL(Constants.BASE_URL,
                "<style>img{display: inline;height: auto;max-width: 100%;}</style>" +
                        "<h1>" + data.getTitle() + "</h1></br>" +
                        data.getTextHtml(), "text/html", "UTF-8", null);
    }
}
