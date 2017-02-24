package name.sportivka.feed.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import name.sportivka.feed.di.fragment.DaggerHubCategoriesComponent;
import name.sportivka.feed.di.fragment.HubCategoriesModule;
import name.sportivka.feed.mvp.presenter.HubCategoriesPresenter;
import name.sportivka.feed.mvp.view.HubCategoriesMvpView;
import name.sportivka.feed.ui.widget.ItemClickSupport;


public class HubCategoriesFragment extends Fragment implements HubCategoriesMvpView {
    @Inject
    HubCategoriesPresenter presenter;

    @BindView(R.id.content_main_recycler_view)
    RecyclerView contentMainRecyclerView;
    Unbinder unbinder;


    public HubCategoriesFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        DaggerHubCategoriesComponent.builder().hubCategoriesModule(new HubCategoriesModule(getContext())).build().inject(this);

        super.onCreate(savedInstanceState);

        presenter.attachView(this);
        presenter.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hub_categories, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        contentMainRecyclerView.setHasFixedSize(true);
        contentMainRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        contentMainRecyclerView.setAdapter(presenter.getHubCategoriesAdapter());
        ItemClickSupport.addTo(contentMainRecyclerView)
                .setOnItemClickListener(presenter.getItemClickListener());

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showProgress() {

    }
}
