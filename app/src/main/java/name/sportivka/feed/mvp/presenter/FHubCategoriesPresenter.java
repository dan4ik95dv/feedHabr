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
import name.sportivka.feed.model.feed.HubCategory;
import name.sportivka.feed.mvp.Presenter;
import name.sportivka.feed.mvp.view.FHubCategoriesMvpView;
import name.sportivka.feed.provider.AsyncData;
import name.sportivka.feed.provider.HubProvider;
import name.sportivka.feed.ui.activity.AHubsActivity;
import name.sportivka.feed.ui.adapter.HubCategoriesAdapter;
import name.sportivka.feed.ui.widget.ItemClickSupport;

/**
 * Created by daniil on 23.02.17.
 */

public class FHubCategoriesPresenter implements Presenter<FHubCategoriesMvpView> {
    @Inject
    Bus bus;
    @Inject
    HubProvider hubProvider;

    private HubCategoriesAdapter hubCategoriesAdapter;
    private ItemClickSupport.OnItemClickListener itemClickListener;
    private FHubCategoriesMvpView fHubCategoriesMvpView;
    private Context context;


    public FHubCategoriesPresenter(Context context) {
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
    public void attachView(FHubCategoriesMvpView view) {
        this.bus.register(this);
        this.fHubCategoriesMvpView = view;
    }

    @Override
    public void detachView() {
        this.bus.unregister(this);
        this.fHubCategoriesMvpView = null;
    }

    public void init() {
        hubCategoriesAdapter = new HubCategoriesAdapter(context);
        itemClickListener = new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                HubCategory hubCategory = hubCategoriesAdapter.getItem(position);
                if (hubCategory != null) {
                    Intent intent = new Intent(context, AHubsActivity.class);
                    intent.putExtra(Constants.HUB_CATEGORY, Parcels.wrap(hubCategory));
                    context.startActivity(intent);
                }

            }
        };
        loadData();
    }

    private void loadData() {
        if (fHubCategoriesMvpView == null) return;
        fHubCategoriesMvpView.showProgress();

        hubProvider.getHubCategories(new AsyncData<List<HubCategory>, FlowCursorList<HubCategory>>() {


            @Override
            public void onSuccess(List<HubCategory> data, int nextPage) {
                hubCategoriesAdapter.setHubCategoryList(data);
                if (fHubCategoriesMvpView == null) return;
                fHubCategoriesMvpView.hideProgress();

            }

            @Override
            public void onSuccessCache(FlowCursorList<HubCategory> data, int nextPage) {
                hubCategoriesAdapter.setHubCategoryList(data);
            }

            @Override
            public void onError() {
                if (fHubCategoriesMvpView == null) return;
                fHubCategoriesMvpView.hideProgress();
            }
        });
    }

    public HubCategoriesAdapter getHubCategoriesAdapter() {
        return hubCategoriesAdapter;
    }

    public ItemClickSupport.OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

}
