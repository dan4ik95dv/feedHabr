package name.sportivka.feed.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Spinner;

import javax.inject.Inject;

import butterknife.BindView;
import name.sportivka.feed.R;
import name.sportivka.feed.di.activity.AMainModule;
import name.sportivka.feed.di.activity.DaggerAMainComponent;
import name.sportivka.feed.mvp.presenter.AMainPresenter;
import name.sportivka.feed.mvp.view.AMainMvpView;
import name.sportivka.feed.ui.fragment.FHubCategoriesFragment;
import name.sportivka.feed.ui.fragment.FPostsFragment;

public class AMainActivity extends BaseActivity
        implements AMainMvpView, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    AMainPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.filter_spinner)
    Spinner filterSpinner;
    @BindView(R.id.container)
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerAMainComponent.builder().aMainModule(new AMainModule(this)).build().inject(this);
        presenter.attachView(this);
        setContentView(R.layout.activity_main);
        presenter.init();
        initToolbar();
        showPosts();
    }

    private void showPosts() {
        setTitle("");
        getSupportActionBar().setTitle("");
        showSpinner(true);
        Bundle bundle = new Bundle();
        bundle.putInt(FPostsFragment.ARG_TYPE, 0);
        showFragment(FPostsFragment.class, bundle);
    }

    private void showHubsCategories() {
        setTitle("Потоки хабов");
        getSupportActionBar().setTitle("Потоки хабов");
        showFragment(FHubCategoriesFragment.class, null);
        showSpinner(false);
    }

    private void showSpinner(boolean show) {
        filterSpinner.setVisibility(show ? View.VISIBLE : View.GONE);
        getSupportActionBar().setDisplayShowTitleEnabled(!show);

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        filterSpinner.setAdapter(presenter.getSpinnerPostFilterAdapter());
        filterSpinner.setOnItemSelectedListener(presenter.getSpinnerPostFilteritemSelectedListener());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_hubs:
                showHubsCategories();
                break;
            case R.id.nav_posts:
                showPosts();
                break;
            case R.id.nav_share:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
