package name.sportivka.feed.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import name.sportivka.feed.R;
import name.sportivka.feed.di.fragment.DaggerFPostsComponent;
import name.sportivka.feed.di.fragment.FPostsModule;
import name.sportivka.feed.model.feed.Hub;
import name.sportivka.feed.mvp.presenter.FPostsPresenter;
import name.sportivka.feed.mvp.view.FPostsMvpView;
import name.sportivka.feed.ui.widget.DividerItemDecoration;
import name.sportivka.feed.ui.widget.EndlessRecyclerViewScrollListener;
import name.sportivka.feed.ui.widget.ItemClickSupport;


public class FPostsFragment extends Fragment implements FPostsMvpView {

    public static final String ARG_TYPE = "type";
    public static final String ARG_HUB = "hub";

    @BindView(R.id.content_main_recycler_view)
    RecyclerView contentMainRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    Unbinder unbinder;
    @Inject
    FPostsPresenter presenter;
    Hub hub;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;

    private int type;
    private EndlessRecyclerViewScrollListener scrollListener;

    public FPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        DaggerFPostsComponent.builder().fPostsModule(new FPostsModule(getContext())).build().inject(this);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(ARG_TYPE);
            hub = Parcels.unwrap(getArguments().getParcelable(ARG_HUB));
        }
        presenter.attachView(this);
        presenter.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        presenter.loadData(type, hub);
        return view;
    }

    private void initView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        contentMainRecyclerView.setHasFixedSize(true);
        contentMainRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(contentMainRecyclerView.getContext(), R.drawable.divider);
        contentMainRecyclerView.addItemDecoration(dividerItemDecoration);
        contentMainRecyclerView.setAdapter(presenter.getPostsAdapter());
        ItemClickSupport.addTo(contentMainRecyclerView)
                .setOnItemClickListener(presenter.getItemClickListener());


        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                presenter.loadData(page);
            }
        };
        contentMainRecyclerView.addOnScrollListener(scrollListener);
        swipeRefreshLayout.setOnRefreshListener(presenter.getRefreshListener());
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        unbinder.unbind();
    }

    @Override
    public void hideProgress() {
        loadingProgress.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), R.string.connection_error_msg, Toast.LENGTH_SHORT).show();
        loadingProgress.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showProgress() {
        loadingProgress.setVisibility(View.VISIBLE);
    }
}
