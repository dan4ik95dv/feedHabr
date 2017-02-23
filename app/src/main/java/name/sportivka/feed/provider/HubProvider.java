package name.sportivka.feed.provider;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.list.FlowQueryList;
import com.raizlabs.android.dbflow.sql.language.From;
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


    public void getHubCategories(final AsyncData<List<HubCategory>> asyncData) {
        if (connectionDetector.isConnectingToInternet())
            getNetworkHubCategories(asyncData);
        else
            getCacheHubCategories();
    }

    public void getHubsForCategory(int page, String category, final AsyncData<List<Hub>> asyncData) {
        if (connectionDetector.isConnectingToInternet())
            getNetworkHubsForCategory(page, category, asyncData);
        else
            getCacheHubs(page, category, asyncData);
    }

    public void getAllHubs(int page, final AsyncData<List<Hub>> asyncData) {
        if (connectionDetector.isConnectingToInternet())
            getNetworkAllHubs(page, asyncData);
        else
            getCacheHubs(page, asyncData);
    }

    void getNetworkHubCategories(final AsyncData<List<HubCategory>> asyncData) {
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

    void getNetworkHubsForCategory(int page, final String category, final AsyncData<List<Hub>> asyncData) {
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

    void getNetworkAllHubs(int page, final AsyncData<List<Hub>> asyncData) {
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

    void getCacheHubs(final int page, final AsyncData<List<Hub>> asyncData) {
        getCacheHubs(page, null, asyncData);
    }

    void getCacheHubs(final int page, final String category, final AsyncData<List<Hub>> asyncData) {
        int offset = (page - 1) * Constants.PER_PAGE;
        From query = SQLite.select().from(Hub.class);
        if (category != null)
            query.where(Hub_Table.category.eq(category));
        FlowQueryList<Hub> result = query.offset(offset).limit(Constants.PER_PAGE).flowQueryList();
        int nextPage = result.getCount() == Constants.PER_PAGE ? page + 1 : 0;
        asyncData.onSuccess(result, nextPage, true);
    }

    FlowQueryList<HubCategory> getCacheHubCategories() {
        return SQLite.select().from(HubCategory.class).flowQueryList();
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


    public interface AsyncData<T> {
        void onSuccess(T data, int nextPage, boolean isCache);

        void onError();
    }
}
