package name.sportivka.feed.mvp.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.raizlabs.android.dbflow.list.FlowCursorList;

import java.util.List;

import javax.inject.Inject;

import name.sportivka.feed.App;
import name.sportivka.feed.model.feed.Hub;
import name.sportivka.feed.mvp.Presenter;
import name.sportivka.feed.mvp.view.HubsMvpView;
import name.sportivka.feed.provider.HubProvider;
import name.sportivka.feed.ui.activity.BaseActivity;
import name.sportivka.feed.ui.adapter.HubsAdapter;
import name.sportivka.feed.ui.widget.ItemClickSupport;

import static name.sportivka.feed.Constants.CATEGORY;
import static name.sportivka.feed.Constants.CATEGORY_TITLE;
import static name.sportivka.feed.Constants.TYPE_ACTION;

/**
 * Created by daniil on 23.02.17.
 */

public class HubsPresenter implements Presenter<HubsMvpView> {

    @Inject
    HubProvider hubProvider;

    private HubsAdapter hubsAdapter;
    private ItemClickSupport.OnItemClickListener itemClickListener;
    private HubsMvpView hubsMvpView;
    private BaseActivity activity;
    private Context context;
    private int typeAction;
    private String category;
    private String categoryTitle;
    private int currentPage = 1;


    public HubsPresenter(Context context) {
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
    public void attachView(HubsMvpView view) {
        this.hubsMvpView = view;
    }

    @Override
    public void detachView() {
        this.hubsMvpView = null;
    }


    public void init() {
        if (activity.getIntent() != null) {
            typeAction = activity.getIntent().getIntExtra(TYPE_ACTION, -1);
            category = activity.getIntent().getStringExtra(CATEGORY);
            categoryTitle = activity.getIntent().getStringExtra(CATEGORY_TITLE);
            if (typeAction != -1) {
                hubsMvpView.changeTitle(categoryTitle);
                hubsAdapter = new HubsAdapter(context);
                itemClickListener = new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

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
        hubsMvpView.showProgress();
        final HubProvider.AsyncData<List<Hub>, FlowCursorList<Hub>> asyncData = new HubProvider.AsyncData<List<Hub>, FlowCursorList<Hub>>() {
            @Override
            public void onSuccess(List<Hub> data, int nextPage, boolean isCache) {
                hubsAdapter.setHubList(data);
                hubsMvpView.hideProgress();
            }

            @Override
            public void onSuccessCache(FlowCursorList<Hub> data, int nextPage) {
                hubsAdapter.setHubList(data);
            }

            @Override
            public void onError() {
                hubsMvpView.hideProgress();
            }
        };
        switch (typeAction) {
            case 0:
                hubProvider.getAllHubs(currentPage, asyncData);
                break;
            case 1:
                hubProvider.getHubsForCategory(currentPage, category, asyncData);
                break;
        }
    }

    public HubsAdapter getHubsAdapter() {
        return hubsAdapter;
    }

    public ItemClickSupport.OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

}
