package name.sportivka.feed.mvp.view;

import name.sportivka.feed.model.feed.Hub;
import name.sportivka.feed.mvp.MvpView;

/**
 * Created by daniil on 23.02.17.
 */

public interface APostsMvpView extends MvpView {
    void changeTitle(String categoryTitle);

    void showPosts(int type, Hub hub);
}
