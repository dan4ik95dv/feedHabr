package name.sportivka.feed.mvp.presenter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.raizlabs.android.dbflow.list.FlowCursorList;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import name.sportivka.feed.App;
import name.sportivka.feed.Constants;
import name.sportivka.feed.event.TypePostsEvent;
import name.sportivka.feed.model.feed.Hub;
import name.sportivka.feed.model.feed.Post;
import name.sportivka.feed.mvp.Presenter;
import name.sportivka.feed.mvp.view.FPostsMvpView;
import name.sportivka.feed.provider.FlowProvider;
import name.sportivka.feed.provider.HubProvider;
import name.sportivka.feed.provider.PostProvider;
import name.sportivka.feed.ui.adapter.PostsAdapter;
import name.sportivka.feed.ui.widget.ItemClickSupport;

/**
 * Created by daniil on 23.02.17.
 */

public class FPostsPresenter implements Presenter<FPostsMvpView> {
    @Inject
    Bus bus;
    @Inject
    FlowProvider flowProvider;
    @Inject
    HubProvider hubProvider;
    @Inject
    PostProvider postProvider;

    private PostsAdapter postsAdapter;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private ItemClickSupport.OnItemClickListener itemClickListener;
    private FPostsMvpView fPostsMvpView;
    private Context context;
    private int currentType = 0;
    private int currentPage = 1;


    public FPostsPresenter(Context context) {
        inject(context);
        this.context = context;
    }

    protected App app() {
        return (App) context.getApplicationContext();
    }

    protected void inject(Context context) {
        ((App) context.getApplicationContext()).appComponent().inject(this);
    }

    @Override
    public void attachView(FPostsMvpView view) {
        this.bus.register(this);
        this.fPostsMvpView = view;
    }

    @Override
    public void detachView() {
        this.bus.unregister(this);
        this.fPostsMvpView = null;
    }

    public void init() {
        postsAdapter = new PostsAdapter(context);
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData(currentType);
            }
        };
    }

    @Subscribe
    public void typePosts(TypePostsEvent event) {
        loadData(event.getType(), event.getHub());
    }


    public void loadData(int type) {
        loadData(type, null);
    }

    public void loadData(int type, Hub hub) {
        fPostsMvpView.showProgress();
        final PostProvider.AsyncData<List<Post>, FlowCursorList<Post>> posts = new PostProvider.AsyncData<List<Post>, FlowCursorList<Post>>() {
            @Override
            public void onSuccess(List<Post> data, int nextPage) {
                postsAdapter.setPostList(data);
                fPostsMvpView.hideProgress();
                currentPage = nextPage;
            }

            @Override
            public void onSuccessCache(FlowCursorList<Post> data, int nextPage) {
                postsAdapter.setPostList(data);
            }

            @Override
            public void onError() {
                fPostsMvpView.showError();
            }
        };
        if (hub != null) {
            postProvider.getHubFeed(currentPage, hub.getAlias(), Constants.TYPES[type], posts);
        } else {
            switch (type) {
                case 0:
                    postProvider.getPubBest("alltime", currentPage, posts);
                    break;
                case 1:
                    postProvider.getPubInteresting(currentPage, posts);
                    break;
                case 2:
                    postProvider.getPubAll(currentPage, posts);
                    break;
            }
        }
    }

    public ItemClickSupport.OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
        return refreshListener;
    }

    public PostsAdapter getPostsAdapter() {
        return postsAdapter;
    }


}