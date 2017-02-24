package name.sportivka.feed.provider;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.list.FlowCursorList;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;

import javax.inject.Inject;

import name.sportivka.feed.Constants;
import name.sportivka.feed.di.scope.AppScope;
import name.sportivka.feed.model.MyDatabase;
import name.sportivka.feed.model.Response;
import name.sportivka.feed.model.feed.Hub;
import name.sportivka.feed.model.feed.HubCategory;
import name.sportivka.feed.model.feed.Hub_Table;
import name.sportivka.feed.network.ConnectionDetector;
import name.sportivka.feed.network.api.HubApi;
import retrofit2.Call;
import retrofit2.Callback;

import static android.text.TextUtils.isEmpty;

/**
 * Created by daniil on 23.02.17.
 */

@AppScope
public class HubProvider {

    HubApi hubApi;

    ConnectionDetector connectionDetector;

    @Inject
    public HubProvider(HubApi hubApi, ConnectionDetector connectionDetector) {
        this.connectionDetector = connectionDetector;
        this.hubApi = hubApi;
    }


    public void getHubCategories(final AsyncData<List<HubCategory>, FlowCursorList<HubCategory>> asyncData) {
        getCacheHubCategories(asyncData);

        if (connectionDetector.isConnectingToInternet())
            getNetworkHubCategories(asyncData);
        else
            asyncData.onError();
    }

    public void getHubsForCategory(int page, String category, final AsyncData<List<Hub>, FlowCursorList<Hub>> asyncData) {
        getCacheHubs(page, category, asyncData);

        if (connectionDetector.isConnectingToInternet())
            getNetworkHubsForCategory(page, category, asyncData);
        else
            asyncData.onError();
    }

    public void getAllHubs(int page, final AsyncData<List<Hub>, FlowCursorList<Hub>> asyncData) {
        getCacheHubs(page, asyncData);

        if (connectionDetector.isConnectingToInternet())
            getNetworkAllHubs(page, asyncData);
        else
            asyncData.onError();


    }

    void getNetworkHubCategories(final AsyncData<List<HubCategory>, FlowCursorList<HubCategory>> asyncData) {
        hubApi.getHubCategories().enqueue(new Callback<Response<List<HubCategory>>>() {
            @Override
            public void onResponse(Call<Response<List<HubCategory>>> call, retrofit2.Response<Response<List<HubCategory>>> response) {
                if (response.isSuccessful()) {
                    putHubCategoriesToCache(response.body().getData());
                    asyncData.onSuccess(response.body().getData(), 0, false);
                } else {
                    asyncData.onError();
                }
            }

            @Override
            public void onFailure(Call<Response<List<HubCategory>>> call, Throwable t) {
                asyncData.onError();
            }
        });
    }

    void getNetworkHubsForCategory(int page, final String category, final AsyncData<List<Hub>, FlowCursorList<Hub>> asyncData) {
        hubApi.getHubsForCategory(category, page).enqueue(new Callback<Response<List<Hub>>>() {
            @Override
            public void onResponse(Call<Response<List<Hub>>> call, retrofit2.Response<Response<List<Hub>>> response) {
                if (response.isSuccessful()) {
                    int nextPage = response.body().getNextPage() != null ? response.body().getNextPage().getNext() : 0;
                    putHubsToCache(response.body().getData(), category);
                    asyncData.onSuccess(response.body().getData(), nextPage, false);
                } else {
                    asyncData.onError();
                }
            }

            @Override
            public void onFailure(Call<Response<List<Hub>>> call, Throwable t) {
                asyncData.onError();
            }
        });
    }

    void getNetworkAllHubs(int page, final AsyncData<List<Hub>, FlowCursorList<Hub>> asyncData) {
        hubApi.getAllHubs(page).enqueue(new Callback<Response<List<Hub>>>() {
            @Override
            public void onResponse(Call<Response<List<Hub>>> call, retrofit2.Response<Response<List<Hub>>> response) {
                if (response.isSuccessful()) {
                    int nextPage = response.body().getNextPage() != null ? response.body().getNextPage().getNext() : 0;
                    putHubsToCache(response.body().getData());
                    asyncData.onSuccess(response.body().getData(), nextPage, false);
                } else {
                    asyncData.onError();
                }
            }

            @Override
            public void onFailure(Call<Response<List<Hub>>> call, Throwable t) {
                asyncData.onError();
            }
        });
    }

    void getCacheHubs(final int page, final AsyncData<List<Hub>, FlowCursorList<Hub>> asyncData) {
        getCacheHubs(page, null, asyncData);
    }

    void getCacheHubs(final int page, final String category, final AsyncData<List<Hub>, FlowCursorList<Hub>> asyncData) {
        int offset = (page - 1) * Constants.PER_PAGE;
        FlowCursorList<Hub> result =
                isEmpty(category) ? SQLite.select().from(Hub.class).offset(offset).limit(Constants.PER_PAGE).cursorList() : SQLite.select().from(Hub.class).where(Hub_Table.category.eq(category)).offset(offset).limit(Constants.PER_PAGE).cursorList();
        int nextPage = result.getCount() == Constants.PER_PAGE ? page + 1 : 0;
        asyncData.onSuccessCache(result, nextPage);
    }

    void getCacheHubCategories(final AsyncData<List<HubCategory>, FlowCursorList<HubCategory>> asyncData) {
        FlowCursorList<HubCategory> result = SQLite.select().from(HubCategory.class).cursorList();
        asyncData.onSuccessCache(result, 0);
    }

    void putHubCategoriesToCache(final List<HubCategory> hubCategories) {
        FlowManager.getDatabase(MyDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<HubCategory>() {
                            @Override
                            public void processModel(HubCategory hubCategory, DatabaseWrapper wrapper) {
                                hubCategory.save();
                            }
                        }).addAll(hubCategories).build())
                .error(new Transaction.Error() {
                    @Override
                    public void onError(Transaction transaction, Throwable error) {

                    }
                })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {

                    }
                }).build().execute();
    }

    void putHubsToCache(final List<Hub> hubs) {
        putHubsToCache(hubs, null);
    }

    void putHubsToCache(final List<Hub> hubs, final String category) {
        FlowManager.getDatabase(MyDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<Hub>() {
                            @Override
                            public void processModel(Hub hub, DatabaseWrapper wrapper) {
                                hub.getFlow().save();
                                if (category != null)
                                    hub.setCategory(category);
                                hub.save();
                            }
                        }).addAll(hubs).build())
                .error(new Transaction.Error() {
                    @Override
                    public void onError(Transaction transaction, Throwable error) {

                    }
                })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {

                    }
                }).build().execute();

    }


    public interface AsyncData<T, F> {
        void onSuccess(T data, int nextPage, boolean isCache);

        void onSuccessCache(F data, int nextPage);

        void onError();
    }
}
