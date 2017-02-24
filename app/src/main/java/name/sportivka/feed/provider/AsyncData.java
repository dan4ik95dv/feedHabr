package name.sportivka.feed.provider;

/**
 * Created by daniil on 25.02.17.
 */
public interface AsyncData<T, F> {
    void onSuccess(T data, int nextPage);

    void onSuccessCache(F data, int nextPage);

    void onError();
}
