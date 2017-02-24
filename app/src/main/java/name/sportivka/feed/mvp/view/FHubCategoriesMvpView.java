package name.sportivka.feed.mvp.view;

import name.sportivka.feed.mvp.MvpView;

/**
 * Created by daniil on 23.02.17.
 */

public interface FHubCategoriesMvpView extends MvpView {
    void hideProgress();

    void showProgress();

    void showError();
}
