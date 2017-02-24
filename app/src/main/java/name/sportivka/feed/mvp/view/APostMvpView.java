package name.sportivka.feed.mvp.view;

import name.sportivka.feed.model.feed.Post;
import name.sportivka.feed.mvp.MvpView;

/**
 * Created by daniil on 23.02.17.
 */

public interface APostMvpView extends MvpView {
    void hideProgress();

    void showProgress();

    void showError();


    void showPost(Post data);
}
