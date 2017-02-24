package name.sportivka.feed.mvp.presenter;

import android.content.Context;
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

    }

    public ArrayAdapter<String> getSpinnerPostFilterAdapter() {
        return spinnerPostFilterAdapter;
    }

    public AdapterView.OnItemSelectedListener getSpinnerPostFilteritemSelectedListener() {
        return spinnerPostFilteritemSelectedListener;
    }
}
