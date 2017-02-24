package name.sportivka.feed.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import name.sportivka.feed.R;
import name.sportivka.feed.di.activity.AHubsModule;
import name.sportivka.feed.di.activity.DaggerAHubsComponent;
import name.sportivka.feed.mvp.presenter.AHubsPresenter;
import name.sportivka.feed.mvp.view.AHubsMvpView;
import name.sportivka.feed.ui.widget.ItemClickSupport;

public class AHubsActivity extends BaseActivity implements AHubsMvpView {

    @Inject
    AHubsPresenter presenter;

    @BindView(R.id.content_main_recycler_view)
    RecyclerView contentMainRecyclerView;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hubs);
        DaggerAHubsComponent.builder().aHubsModule(new AHubsModule(this)).build().inject(this);
        initToolbar();
        presenter.attachView(this);
        presenter.init();
        initView();
    }

    private void initView() {
        contentMainRecyclerView.setHasFixedSize(true);
        contentMainRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        contentMainRecyclerView.setAdapter(presenter.getHubsAdapter());
        ItemClickSupport.addTo(contentMainRecyclerView)
                .setOnItemClickListener(presenter.getItemClickListener());
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
        loadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        loadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void changeTitle(String categoryTitle) {
        setTitle(categoryTitle);
        getSupportActionBar().setTitle(categoryTitle);
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.connection_error_msg, Toast.LENGTH_SHORT).show();
        loadingProgress.setVisibility(View.GONE);
    }
}
