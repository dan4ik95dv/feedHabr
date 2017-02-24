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
import name.sportivka.feed.model.feed.Flow;
import name.sportivka.feed.model.feed.Flow_Table;
import name.sportivka.feed.model.feed.Hub;
import name.sportivka.feed.model.feed.Post;
import name.sportivka.feed.network.ConnectionDetector;
import name.sportivka.feed.network.api.FlowApi;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by daniil on 23.02.17.
 */

@AppScope
public class FlowProvider {

    FlowApi flowApi;

    ConnectionDetector connectionDetector;

    @Inject
    public FlowProvider(FlowApi flowApi, ConnectionDetector connectionDetector) {
        this.connectionDetector = connectionDetector;
        this.flowApi = flowApi;
    }

    public void getFlowHubFeed(String flow, String type, int page, final AsyncData<List<Post>> asyncData) {
        if (connectionDetector.isConnectingToInternet())
            getNetworkFlowHubFeed(page, flow, type, asyncData);
        else
            getCacheFlowHubFeed(page, flow, type, asyncData);
    }


    public void getFlows(final AsyncData<List<Flow>> asyncData) {
        if (connectionDetector.isConnectingToInternet())
            getNetworkFlows(asyncData);
        else
            getCacheFlows(asyncData);
    }


    public void getFlowHubs(int page, String flow, final AsyncData<List<Hub>> asyncData) {
        if (connectionDetector.isConnectingToInternet())
            getNetworkFlowHubs(page, flow, asyncData);
        else
            getCacheFlowHubs(page, flow, asyncData);
    }

    void getNetworkFlows(final AsyncData<List<Flow>> asyncData) {
        flowApi.getFlows().enqueue(new Callback<Response<List<Flow>>>() {
            @Override
            public void onResponse(Call<Response<List<Flow>>> call, retrofit2.Response<Response<List<Flow>>> response) {
                if (response.isSuccessful()) {
                    putFlowsToCache(response.body().getData());
                    asyncData.onSuccess(response.body().getData(), 0, false);
                } else {
                    asyncData.onError();
                }
            }

            @Override
            public void onFailure(Call<Response<List<Flow>>> call, Throwable t) {
                asyncData.onError();
            }
        });
    }

    void getNetworkFlowHubFeed(int page, String flow, String type, final AsyncData<List<Post>> asyncData) {
        flowApi.getFlowHubFeed(flow, type, page, Constants.INCLUDE, Constants.EXCLUDE_WITH_FLOW, Constants.PER_PAGE)
                .enqueue(new Callback<Response<List<Post>>>() {
                    @Override
                    public void onResponse(Call<Response<List<Post>>> call, retrofit2.Response<Response<List<Post>>> response) {
                        if (response.isSuccessful()) {
                            int nextPage = response.body().getNextPage() != null ? response.body().getNextPage().getNext() : 0;
                            putPostsToCache(response.body().getData());
                            asyncData.onSuccess(response.body().getData(), nextPage, false);
                        } else {
                            asyncData.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<List<Post>>> call, Throwable t) {
                        asyncData.onError();
                    }
                });
    }

    void getNetworkFlowHubs(int page, String flow, final AsyncData<List<Hub>> asyncData) {
        flowApi.getFlowHubs(flow, page).enqueue(new Callback<Response<List<Hub>>>() {
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

    void getCacheFlowHubs(final int page, final AsyncData<List<Hub>> asyncData) {
        getCacheFlowHubs(page, null, asyncData);
    }

    void getCacheFlowHubs(final int page, final String flow, final AsyncData<List<Hub>> asyncData) {
        int offset = (page - 1) * Constants.PER_PAGE;
        From query = SQLite.select().from(Hub.class);
        if (flow != null)
            query.where(Flow_Table.name.eq(flow));
        FlowQueryList<Hub> result = query.offset(offset).limit(Constants.PER_PAGE).flowQueryList();
        int nextPage = result.getCount() == Constants.PER_PAGE ? page + 1 : 0;
        asyncData.onSuccess(result, nextPage, true);
    }

    void getCacheFlowHubFeed(int page, String flow, String type, AsyncData<List<Post>> asyncData) {
        FlowQueryList<Post> result = SQLite.select().from(Post.class).where(Flow_Table.name.eq(flow)).flowQueryList();
        int nextPage = result.getCount() == Constants.PER_PAGE ? page + 1 : 0;
        asyncData.onSuccess(result, nextPage, true);
    }

    void getCacheFlows(AsyncData<List<Flow>> asyncData) {
        FlowQueryList<Flow> result = SQLite.select().from(Flow.class).flowQueryList();
        asyncData.onSuccess(result, 0, true);
    }

    void putFlowsToCache(final List<Flow> flows) {
        FlowManager.getDatabase(MyDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<Flow>() {
                            @Override
                            public void processModel(Flow flow, DatabaseWrapper wrapper) {
                                flow.save();
                            }
                        }).addAll(flows).build())
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

    void putHubsToCache(final List<Hub> hubs, final String flow) {
        FlowManager.getDatabase(MyDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<Hub>() {
                            @Override
                            public void processModel(Hub hub, DatabaseWrapper wrapper) {
                                hub.getFlow().save();
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


    void putPostsToCache(final List<Post> posts) {
        FlowManager.getDatabase(MyDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<Post>() {
                            @Override
                            public void processModel(Post post, DatabaseWrapper wrapper) {
                                post.getAuthor().save();
                                post.getAuthor().getGeo().save();
                                post.getAuthor().getCounters().save();
                                post.getFlow().save();
                                for (Hub hub : post.getHubs()) {
                                    hub.save();
                                }
                                post.save();
                            }
                        }).addAll(posts).build())
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
