package name.sportivka.feed.mvp.view;

import name.sportivka.feed.mvp.MvpView;

/**
 * Created by daniil on 23.02.17.
 */

public interface FPostsMvpView extends MvpView {
    void hideProgress();

    void showError();

    void showProgress();
}
