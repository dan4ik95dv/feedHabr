package name.sportivka.feed.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.raizlabs.android.dbflow.list.FlowCursorList;
import com.squareup.otto.Bus;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import name.sportivka.feed.App;
import name.sportivka.feed.Constants;
import name.sportivka.feed.model.feed.Hub;
import name.sportivka.feed.model.feed.HubCategory;
import name.sportivka.feed.mvp.Presenter;
import name.sportivka.feed.mvp.view.AHubsMvpView;
import name.sportivka.feed.provider.HubProvider;
import name.sportivka.feed.ui.activity.APostsActivity;
import name.sportivka.feed.ui.activity.BaseActivity;
import name.sportivka.feed.ui.adapter.HubsAdapter;
import name.sportivka.feed.ui.widget.ItemClickSupport;

import static android.text.TextUtils.isEmpty;
import static name.sportivka.feed.Constants.HUB_CATEGORY;

/**
 * Created by daniil on 23.02.17.
 */

public class AHubsPresenter implements Presenter<AHubsMvpView> {

    @Inject
    HubProvider hubProvider;
    @Inject
    Bus bus;

    private HubsAdapter hubsAdapter;
    private ItemClickSupport.OnItemClickListener itemClickListener;
    private AHubsMvpView aHubsMvpView;
    private BaseActivity activity;
    private Context context;
    private int typeAction;
    private HubCategory hubCategory;
    private int currentPage = 1;


    public AHubsPresenter(Context context) {
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
    public void attachView(AHubsMvpView view) {
        this.bus.register(this);
        this.aHubsMvpView = view;
    }

    @Override
    public void detachView() {
        this.bus.unregister(this);
        this.aHubsMvpView = null;
    }


    public void init() {
        if (activity.getIntent() != null) {
            hubCategory = Parcels.unwrap(activity.getIntent().getParcelableExtra(HUB_CATEGORY));
            if (hubCategory != null) {
                aHubsMvpView.changeTitle(hubCategory.getTitle());
                hubsAdapter = new HubsAdapter(context);
                itemClickListener = new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Hub hub = hubsAdapter.getItem(position);
                        if (hub != null) {
                            Intent intent = new Intent(context, APostsActivity.class);
                            intent.putExtra(Constants.HUB, Parcels.wrap(hub));
                            context.startActivity(intent);
                        }
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
        aHubsMvpView.showProgress();
        final HubProvider.AsyncData<List<Hub>, FlowCursorList<Hub>> asyncData = new HubProvider.AsyncData<List<Hub>, FlowCursorList<Hub>>() {

            @Override
            public void onSuccess(List<Hub> data, int nextPage) {
                hubsAdapter.setHubList(data);
                aHubsMvpView.hideProgress();
            }

            @Override
            public void onSuccessCache(FlowCursorList<Hub> data, int nextPage) {
                hubsAdapter.setHubList(data);
            }

            @Override
            public void onError() {
                aHubsMvpView.hideProgress();
            }
        };

        if (isEmpty(hubCategory.getAlias())) {
            hubProvider.getAllHubs(currentPage, asyncData);
        } else {
            hubProvider.getHubsForCategory(currentPage, hubCategory.getAlias(), asyncData);
        }
    }

    public HubsAdapter getHubsAdapter() {
        return hubsAdapter;
    }

    public ItemClickSupport.OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

}
