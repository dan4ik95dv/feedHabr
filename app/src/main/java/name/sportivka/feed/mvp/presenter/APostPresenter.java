package name.sportivka.feed.mvp.presenter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import name.sportivka.feed.App;
import name.sportivka.feed.model.feed.Post;
import name.sportivka.feed.mvp.Presenter;
import name.sportivka.feed.mvp.view.APostMvpView;
import name.sportivka.feed.provider.PostProvider;
import name.sportivka.feed.ui.activity.BaseActivity;

import static name.sportivka.feed.Constants.POST_ID;

/**
 * Created by daniil on 23.02.17.
 */

public class APostPresenter implements Presenter<APostMvpView> {

    @Inject
    Bus bus;
    @Inject
    PostProvider postProvider;

    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private APostMvpView aPostMvpView;
    private BaseActivity activity;
    private Context context;
    private Long postId;

    public APostPresenter(Context context) {
        inject(context);
        this.activity = (BaseActivity) context;
        this.context = context;
    }

    protected App app() {
        return (App) context.getApplicationContext();
    }

    protected void inject(Context context) {
        ((App) context.getApplicationContext()).appComponent().inject(this);
    }


    @Override
    public void attachView(APostMvpView view) {
        this.bus.register(this);
        this.aPostMvpView = view;
    }

    @Override
    public void detachView() {
        this.bus.unregister(this);
        this.aPostMvpView = null;
    }


    public void init() {
        if (activity.getIntent() != null) {
            postId = activity.getIntent().getLongExtra(POST_ID, -1);
            if (postId != -1) {
                refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        loadData();
                    }
                };

                loadData();
            } else
                activity.finish();

        } else {
            activity.finish();
        }
    }


    private void loadData() {
        if (aPostMvpView == null) return;
        aPostMvpView.showProgress();

        postProvider.getPost(postId, true, new PostProvider.AsyncData<Post, Post>() {
            @Override
            public void onSuccess(Post data, int nextPage) {
                if (aPostMvpView == null) return;
                aPostMvpView.hideProgress();
                aPostMvpView.showPost(data);
            }

            @Override
            public void onSuccessCache(Post data, int nextPage) {
                aPostMvpView.showPost(data);
            }

            @Override
            public void onError() {
                if (aPostMvpView == null) return;
                aPostMvpView.hideProgress();
            }
        });
    }

    public SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
        return refreshListener;
    }
}
