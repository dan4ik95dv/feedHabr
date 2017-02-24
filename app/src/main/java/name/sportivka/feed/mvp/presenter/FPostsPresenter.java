package name.sportivka.feed.mvp.presenter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.raizlabs.android.dbflow.list.FlowCursorList;

import java.util.List;

import javax.inject.Inject;

import name.sportivka.feed.App;
import name.sportivka.feed.R;
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
    FlowProvider flowProvider;
    @Inject
    HubProvider hubProvider;
    @Inject
    PostProvider postProvider;

    private ArrayAdapter<String> spinnerPostFilterAdapter;
    private AdapterView.OnItemSelectedListener spinnerPostFilteritemSelectedListener;
    private PostsAdapter postsAdapter;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private ItemClickSupport.OnItemClickListener itemClickListener;
    private FPostsMvpView FPostsMvpView;
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
        this.FPostsMvpView = view;
    }

    @Override
    public void detachView() {
        this.FPostsMvpView = null;
    }

    public void init() {
        postsAdapter = new PostsAdapter(context);
        String[] filterArray = context.getResources().getStringArray(R.array.post_filter);
        spinnerPostFilterAdapter = new ArrayAdapter<>(
                context, R.layout.filter_title, filterArray);
        spinnerPostFilterAdapter.setDropDownViewResource(R.layout.filter_list);
        spinnerPostFilteritemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (currentType == position) return;
                currentType = position;
                currentPage = 1;
                loadData(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData(currentType);
            }
        };
        itemClickListener = new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

            }
        };
    }

    public void loadData(int type) {
        FPostsMvpView.showProgress();
        final PostProvider.AsyncData<List<Post>, FlowCursorList<Post>> posts = new PostProvider.AsyncData<List<Post>, FlowCursorList<Post>>() {
            @Override
            public void onSuccess(List<Post> data, int nextPage, boolean isCache) {
                postsAdapter.setPostList(data);
                FPostsMvpView.hideProgress();
                currentPage = nextPage;
            }

            @Override
            public void onSuccessCache(FlowCursorList<Post> data, int nextPage) {
                postsAdapter.setPostList(data);
            }

            @Override
            public void onError() {
                FPostsMvpView.showError();
            }
        };

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

    public ItemClickSupport.OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
        return refreshListener;
    }

    public PostsAdapter getPostsAdapter() {
        return postsAdapter;
    }

    public ArrayAdapter<String> getSpinnerPostFilterAdapter() {
        return spinnerPostFilterAdapter;
    }

    public AdapterView.OnItemSelectedListener getSpinnerPostFilteritemSelectedListener() {
        return spinnerPostFilteritemSelectedListener;
    }
}
