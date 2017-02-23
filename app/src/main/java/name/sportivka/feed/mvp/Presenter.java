package name.sportivka.feed.mvp;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}