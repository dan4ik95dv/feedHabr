package name.sportivka.feed.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import name.sportivka.feed.R;
import name.sportivka.feed.di.activity.APostsModule;
import name.sportivka.feed.di.activity.DaggerAPostsComponent;
import name.sportivka.feed.model.feed.Hub;
import name.sportivka.feed.mvp.presenter.APostsPresenter;
import name.sportivka.feed.mvp.view.APostsMvpView;
import name.sportivka.feed.ui.fragment.FPostsFragment;

public class APostsActivity extends BaseActivity implements APostsMvpView {
    @Inject
    APostsPresenter presenter;

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.filter_spinner)
    Spinner filterSpinner;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_posts_textview)
    TextView titlePostsTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        ButterKnife.bind(this);
        DaggerAPostsComponent.builder().aPostsModule(new APostsModule(this)).build().inject(this);
        presenter.attachView(this);
        presenter.init();
        initToolbar();
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);
        filterSpinner.setAdapter(presenter.getSpinnerPostFilterAdapter());
        filterSpinner.setOnItemSelectedListener(presenter.getSpinnerPostFilteritemSelectedListener());
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
    public void changeTitle(String categoryTitle) {
        titlePostsTextview.setText(categoryTitle);
    }

    @Override
    public void showPosts(int type, Hub hub) {
        Bundle bundle = new Bundle();
        bundle.putInt(FPostsFragment.ARG_TYPE, 0);
        if (hub != null) {
            bundle.putParcelable(FPostsFragment.ARG_HUB, Parcels.wrap(hub));
        }
        showFragment(FPostsFragment.class, bundle);
    }

}
