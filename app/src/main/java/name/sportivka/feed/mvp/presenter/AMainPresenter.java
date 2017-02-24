package name.sportivka.feed.mvp.presenter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import name.sportivka.feed.App;
import name.sportivka.feed.R;
import name.sportivka.feed.event.TypePostsEvent;
import name.sportivka.feed.mvp.Presenter;
import name.sportivka.feed.mvp.view.AMainMvpView;
import name.sportivka.feed.provider.FlowProvider;
import name.sportivka.feed.provider.HubProvider;
import name.sportivka.feed.provider.PostProvider;
import name.sportivka.feed.ui.adapter.PostsAdapter;
import name.sportivka.feed.ui.widget.ItemClickSupport;

/**
 * Created by daniil on 23.02.17.
 */

public class AMainPresenter implements Presenter<AMainMvpView> {

    @Inject
    FlowProvider flowProvider;
    @Inject
    HubProvider hubProvider;
    @Inject
    PostProvider postProvider;
    @Inject
    Bus bus;

    private ArrayAdapter<String> spinnerPostFilterAdapter;
    private AdapterView.OnItemSelectedListener spinnerPostFilteritemSelectedListener;
    private PostsAdapter postsAdapter;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private ItemClickSupport.OnItemClickListener itemClickListener;
    private AMainMvpView AMainMvpView;
    private Context context;
    private int currentType = 0;
    private int currentPage = 1;


    public AMainPresenter(Context context) {
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
    public void attachView(AMainMvpView view) {
        this.bus.register(this);
        this.AMainMvpView = view;
    }

    @Override
    public void detachView() {
        this.bus.unregister(this);
        this.AMainMvpView = null;
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
                currentPage = 1;
                currentType = position;
                bus.post(new TypePostsEvent(currentType));
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

    private void loadData(int currentType) {
        // TODO: 24.02.17 Sent to fragment
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
