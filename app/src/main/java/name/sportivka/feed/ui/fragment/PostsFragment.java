package name.sportivka.feed.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import name.sportivka.feed.R;
import name.sportivka.feed.di.fragment.DaggerPostsComponent;
import name.sportivka.feed.di.fragment.PostsModule;
import name.sportivka.feed.mvp.presenter.PostsPresenter;
import name.sportivka.feed.mvp.view.PostsMvpView;
import name.sportivka.feed.ui.widget.DividerItemDecoration;
import name.sportivka.feed.ui.widget.ItemClickSupport;


public class PostsFragment extends Fragment implements PostsMvpView {

    public static final String ARG_TYPE = "type";

    @BindView(R.id.content_main_recycler_view)
    RecyclerView contentMainRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    Unbinder unbinder;
    @Inject
    PostsPresenter presenter;
    private int type;

    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        DaggerPostsComponent.builder().postsModule(new PostsModule(getContext())).build().inject(this);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(ARG_TYPE);
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
        presenter.loadData(type);
        return view;
    }

    private void initView() {
        contentMainRecyclerView.setHasFixedSize(true);
        contentMainRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(contentMainRecyclerView.getContext(), R.drawable.divider);
        contentMainRecyclerView.addItemDecoration(dividerItemDecoration);
        contentMainRecyclerView.setAdapter(presenter.getPostsAdapter());
        ItemClickSupport.addTo(contentMainRecyclerView)
                .setOnItemClickListener(presenter.getItemClickListener());

        swipeRefreshLayout.setOnRefreshListener(presenter.getRefreshListener());
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
}
