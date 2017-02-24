package name.sportivka.feed.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import name.sportivka.feed.ui.activity.APostActivity;
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
    private Hub hub;
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
                loadData(currentType, hub, true, false);
            }
        };
        itemClickListener = new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Post post = postsAdapter.getItem(position);
                if (post == null) return;
                Intent intent = new Intent(context, APostActivity.class);
                intent.putExtra(Constants.POST_ID, post.getId());
                intent.putExtra(Constants.TITLE_POST, post.getTitle());
                context.startActivity(intent);
            }
        };
    }

    @Subscribe
    public void typePosts(TypePostsEvent event) {
        currentType = event.getType();
        hub = event.getHub();
        currentPage = 1;
        loadData(event.getType(), event.getHub());
    }

    public void loadData(int page) {
        currentPage = page;
        loadData(currentType, hub, false, true);
    }

    public void loadData(int type, Hub hub) {
        loadData(type, hub, false, false);
    }

    public void loadData(int type, Hub hub, boolean update, final boolean append) {
        postProvider.setCacheEnable(!append);
        if (fPostsMvpView == null) return;
        if (update) currentPage = 1;
        fPostsMvpView.showProgress();
        final PostProvider.AsyncData<List<Post>, FlowCursorList<Post>> posts = new PostProvider.AsyncData<List<Post>, FlowCursorList<Post>>() {
            @Override
            public void onSuccess(List<Post> data, int nextPage) {
                if (fPostsMvpView == null) return;
                fPostsMvpView.hideProgress();
                if (append)
                    postsAdapter.addPostList(data);
                else
                    postsAdapter.setPostList(data);

                currentPage = nextPage;
            }

            @Override
            public void onSuccessCache(FlowCursorList<Post> data, int nextPage) {
                postsAdapter.setPostList(data);
            }

            @Override
            public void onError() {
                if (fPostsMvpView == null) return;
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
