package name.sportivka.feed.mvp.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.raizlabs.android.dbflow.list.FlowCursorList;

import java.util.List;

import javax.inject.Inject;

import name.sportivka.feed.App;
import name.sportivka.feed.model.feed.HubCategory;
import name.sportivka.feed.mvp.Presenter;
import name.sportivka.feed.mvp.view.HubCategoriesMvpView;
import name.sportivka.feed.provider.HubProvider;
import name.sportivka.feed.ui.adapter.HubCategoriesAdapter;
import name.sportivka.feed.ui.widget.ItemClickSupport;

/**
 * Created by daniil on 23.02.17.
 */

public class HubCategoriesPresenter implements Presenter<HubCategoriesMvpView> {

    @Inject
    HubProvider hubProvider;

    private HubCategoriesAdapter hubCategoriesAdapter;
    private ItemClickSupport.OnItemClickListener itemClickListener;
    private HubCategoriesMvpView hubCategoriesMvpView;
    private Context context;


    public HubCategoriesPresenter(Context context) {
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
    public void attachView(HubCategoriesMvpView view) {
        this.hubCategoriesMvpView = view;
    }

    @Override
    public void detachView() {
        this.hubCategoriesMvpView = null;
    }

    public void init() {
        hubCategoriesAdapter = new HubCategoriesAdapter(context);
        itemClickListener = new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

            }
        };
        loadData();
    }

    private void loadData() {
        hubCategoriesMvpView.showProgress();
        hubProvider.getHubCategories(new HubProvider.AsyncData<List<HubCategory>, FlowCursorList<HubCategory>>() {
            @Override
            public void onSuccess(List<HubCategory> data, int nextPage, boolean isCache) {
                hubCategoriesAdapter.setHubCategoryList(data);
                hubCategoriesMvpView.hideProgress();
            }

            @Override
            public void onSuccessCache(FlowCursorList<HubCategory> data, int nextPage) {
                hubCategoriesAdapter.setHubCategoryList(data);
            }

            @Override
            public void onError() {
                hubCategoriesMvpView.hideProgress();
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
