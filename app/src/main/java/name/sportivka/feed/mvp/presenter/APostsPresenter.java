package name.sportivka.feed.mvp.presenter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.squareup.otto.Bus;

import org.parceler.Parcels;

import javax.inject.Inject;

import name.sportivka.feed.App;
import name.sportivka.feed.R;
import name.sportivka.feed.event.TypePostsEvent;
import name.sportivka.feed.model.feed.Hub;
import name.sportivka.feed.mvp.Presenter;
import name.sportivka.feed.mvp.view.APostsMvpView;
import name.sportivka.feed.ui.activity.BaseActivity;

import static name.sportivka.feed.Constants.HUB;

/**
 * Created by daniil on 23.02.17.
 */

public class APostsPresenter implements Presenter<APostsMvpView> {

    @Inject
    Bus bus;

    private ArrayAdapter<String> spinnerPostFilterAdapter;
    private AdapterView.OnItemSelectedListener spinnerPostFilteritemSelectedListener;
    private APostsMvpView aPostsMvpView;
    private BaseActivity activity;
    private Context context;
    private Hub hub;
    private int currentType = 0;


    public APostsPresenter(Context context) {
        inject(context);

        this.activity = (BaseActivity) context;
        this.context = context;
    }

    protected App app() {
        return (App) context.getApplicationContext();
    }


    public void init() {
        if (activity.getIntent() != null) {
            hub = Parcels.unwrap(activity.getIntent().getParcelableExtra(HUB));
            if (hub != null) {
                String[] filterArray = context.getResources().getStringArray(R.array.post_filter);
                spinnerPostFilterAdapter = new ArrayAdapter<>(
                        context, R.layout.filter_title_posts, filterArray);
                spinnerPostFilterAdapter.setDropDownViewResource(R.layout.filter_list);
                spinnerPostFilteritemSelectedListener = new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        if (currentType == position) return;
                        currentType = position;
                        bus.post(new TypePostsEvent(hub, currentType));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                };
                aPostsMvpView.changeTitle(hub.getTitle());
                aPostsMvpView.showPosts(currentType, hub);
            } else
                activity.finish();

        } else {
            activity.finish();
        }
    }

    protected void inject(Context context) {
        ((App) context.getApplicationContext()).appComponent().inject(this);
    }

    @Override
    public void attachView(APostsMvpView view) {
        this.bus.register(this);
        this.aPostsMvpView = view;
    }

    @Override
    public void detachView() {
        this.bus.unregister(this);
        this.aPostsMvpView = null;
    }


    public ArrayAdapter<String> getSpinnerPostFilterAdapter() {
        return spinnerPostFilterAdapter;
    }

    public AdapterView.OnItemSelectedListener getSpinnerPostFilteritemSelectedListener() {
        return spinnerPostFilteritemSelectedListener;
    }
}
