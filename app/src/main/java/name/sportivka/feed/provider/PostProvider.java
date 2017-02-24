package name.sportivka.feed.provider;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.list.FlowCursorList;
import com.raizlabs.android.dbflow.sql.language.ConditionGroup;
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
import name.sportivka.feed.model.feed.Post;
import name.sportivka.feed.model.feed.Post_Table;
import name.sportivka.feed.network.ConnectionDetector;
import name.sportivka.feed.network.api.PostApi;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by daniil on 23.02.17.
 */

@AppScope
public class PostProvider {

    PostApi postApi;

    ConnectionDetector connectionDetector;

    @Inject
    public PostProvider(PostApi postApi, ConnectionDetector connectionDetector) {
        this.connectionDetector = connectionDetector;
        this.postApi = postApi;
    }

    public void getPost(long postId, boolean getArticle, AsyncData<Post, Post> asyncData) {
        getCachePost(postId, getArticle, asyncData);

        if (connectionDetector.isConnectingToInternet())
            getNetworkPost(postId, getArticle, asyncData);
        else
            asyncData.onError();
    }

    public void getPubAll(int page, AsyncData<List<Post>, FlowCursorList<Post>> asyncData) {
        getCachePubAll(page, asyncData);

        if (connectionDetector.isConnectingToInternet())
            getNetworkPubAll(page, asyncData);
        else
            asyncData.onError();
    }


    public void getPubBest(String type, int page, AsyncData<List<Post>, FlowCursorList<Post>> asyncData) {
        getCachePubBest(type, page, asyncData);

        if (connectionDetector.isConnectingToInternet())
            getNetworkPubBest(type, page, asyncData);
        else
            asyncData.onError();
    }

    public void getPubInteresting(int page, AsyncData<List<Post>, FlowCursorList<Post>> asyncData) {
        getCachePubInteresting(page, asyncData);

        if (connectionDetector.isConnectingToInternet())
            getNetworkPubInteresting(page, asyncData);
        else
            asyncData.onError();
    }

    public void searchPosts(String search, int page, String sort, boolean getArticle, AsyncData<List<Post>, FlowCursorList<Post>> pasyncDatasts) {
        getCacheSearchPosts(search, page, sort, getArticle, pasyncDatasts);

        if (connectionDetector.isConnectingToInternet())
            getNetworkSearchPosts(search, page, sort, getArticle, pasyncDatasts);
        else
            pasyncDatasts.onError();
    }

    public void getHubFeed(int page, String hub, String type, PostProvider.AsyncData<List<Post>, FlowCursorList<Post>> asyncData) {
        getCacheHubFeed(page, hub, type, asyncData);
        if (connectionDetector.isConnectingToInternet())
            getNetworkHubFeed(page, hub, type, asyncData);
        else
            asyncData.onError();
    }

    private void getNetworkHubFeed(int page, String hub, String type, final PostProvider.AsyncData<List<Post>, FlowCursorList<Post>> asyncData) {
        postApi.getHubFeed(hub, type, page, Constants.INCLUDE, Constants.EXCLUDE_WITHOUT_FLOW, Constants.PER_PAGE).enqueue(new Callback<Response<List<Post>>>() {
            @Override
            public void onResponse(Call<Response<List<Post>>> call, retrofit2.Response<Response<List<Post>>> response) {
                if (response.isSuccessful()) {
                    putPostsToCache(response.body().getData());
                    asyncData.onSuccess(response.body().getData(), getNextPage(response.body()));
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

    private void getCacheHubFeed(int page, String hub, String type, PostProvider.AsyncData<List<Post>, FlowCursorList<Post>> asyncData) {
        FlowCursorList<Post> result = SQLite.select().from(Post.class).where(Post_Table.hubsAliases.like("%" + hub + "%")).orderBy(Post_Table.timePublished, false).cursorList();
        int nextPage = result.getCount() == Constants.PER_PAGE ? page + 1 : 0;
        asyncData.onSuccessCache(result, nextPage);
    }

    private void getNetworkSearchPosts(String search, int page, String sort, boolean getArticle, final AsyncData<List<Post>, FlowCursorList<Post>> asyncData) {
        postApi.searchPosts(search, page, sort, getArticle).enqueue(new Callback<Response<List<Post>>>() {
            @Override
            public void onResponse(Call<Response<List<Post>>> call, retrofit2.Response<Response<List<Post>>> response) {
                if (response.isSuccessful()) {
                    putPostsToCache(response.body().getData());
                    asyncData.onSuccess(response.body().getData(), getNextPage(response.body()));
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

    private void getCacheSearchPosts(String search, int page, String sort, boolean getArticle, AsyncData<List<Post>, FlowCursorList<Post>> asyncData) {
        ConditionGroup conditionGroup = ConditionGroup.clause()
                .or(Post_Table.title.like("%" + search + "%"))
                .or(Post_Table.previewHtml.like("%" + search + "%"))
                .or(Post_Table.tagsString.like("%" + search + "%"))
                .or(Post_Table.textHtml.like("%" + search + "%"));

        FlowCursorList<Post> result = SQLite.select().from(Post.class).where(conditionGroup).cursorList();
        int nextPage = result.getCount() == Constants.PER_PAGE ? page + 1 : 0;
        asyncData.onSuccessCache(result, nextPage);
    }

    private void getNetworkPubInteresting(int page, final AsyncData<List<Post>, FlowCursorList<Post>> asyncData) {
        postApi.getPubInteresting(page, Constants.INCLUDE, Constants.EXCLUDE_WITHOUT_FLOW, Constants.PER_PAGE).enqueue(new Callback<Response<List<Post>>>() {
            @Override
            public void onResponse(Call<Response<List<Post>>> call, retrofit2.Response<Response<List<Post>>> response) {
                if (response.isSuccessful()) {
                    putPostsToCache(response.body().getData());
                    asyncData.onSuccess(response.body().getData(), getNextPage(response.body()));
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

    private void getCachePubInteresting(int page, AsyncData<List<Post>, FlowCursorList<Post>> asyncData) {
        FlowCursorList<Post> result = SQLite.select().from(Post.class).where(Post_Table.interesting.eq(true)).cursorList();
        int nextPage = result.getCount() == Constants.PER_PAGE ? page + 1 : 0;
        asyncData.onSuccessCache(result, nextPage);
    }

    private void getNetworkPubBest(String type, int page, final AsyncData<List<Post>, FlowCursorList<Post>> asyncData) {
        postApi.getPubBest(type, page, Constants.INCLUDE, Constants.EXCLUDE_WITHOUT_FLOW, Constants.PER_PAGE).enqueue(new Callback<Response<List<Post>>>() {
            @Override
            public void onResponse(Call<Response<List<Post>>> call, retrofit2.Response<Response<List<Post>>> response) {
                if (response.isSuccessful()) {
                    putPostsToCache(response.body().getData());
                    asyncData.onSuccess(response.body().getData(), getNextPage(response.body()));
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

    private void getCachePubBest(String type, int page, AsyncData<List<Post>, FlowCursorList<Post>> asyncData) {
        FlowCursorList<Post> result = SQLite.select().from(Post.class).orderBy(Post_Table.score, false).cursorList();
        int nextPage = result.getCount() == Constants.PER_PAGE ? page + 1 : 0;
        asyncData.onSuccessCache(result, nextPage);
    }

    private void getNetworkPubAll(int page, final AsyncData<List<Post>, FlowCursorList<Post>> asyncData) {
        postApi.getPubAll(page, Constants.INCLUDE, Constants.EXCLUDE_WITHOUT_FLOW, Constants.PER_PAGE).enqueue(new Callback<Response<List<Post>>>() {
            @Override
            public void onResponse(Call<Response<List<Post>>> call, retrofit2.Response<Response<List<Post>>> response) {
                if (response.isSuccessful()) {
                    putPostsToCache(response.body().getData());
                    asyncData.onSuccess(response.body().getData(), getNextPage(response.body()));
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

    private void getCachePubAll(int page, AsyncData<List<Post>, FlowCursorList<Post>> asyncData) {
        FlowCursorList<Post> result = SQLite.select().from(Post.class).orderBy(Post_Table.timePublished, false).cursorList();
        int nextPage = result.getCount() == Constants.PER_PAGE ? page + 1 : 0;
        asyncData.onSuccessCache(result, nextPage);
    }


    private void getNetworkPost(long postId, boolean getArticle, final AsyncData<Post, Post> asyncData) {
        postApi.getPost(postId, getArticle).enqueue(new Callback<Response<Post>>() {
            @Override
            public void onResponse(Call<Response<Post>> call, retrofit2.Response<Response<Post>> response) {
                if (response.isSuccessful()) {
                    putPostToCache(response.body().getData());

                    asyncData.onSuccess(response.body().getData(), getNextPage(response.body()));
                } else {
                    asyncData.onError();
                }
            }

            @Override
            public void onFailure(Call<Response<Post>> call, Throwable t) {
                asyncData.onError();
            }
        });
    }

    private void getCachePost(long postId, boolean getArticle, AsyncData<Post, Post> asyncData) {
        Post result = SQLite.select().from(Post.class).where(Post_Table.myId.eq(postId)).querySingle();
        asyncData.onSuccessCache(result, 0);
    }

    void putPostToCache(final Post post) {
        FlowManager.getDatabase(MyDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<Post>() {
                            @Override
                            public void processModel(Post post, DatabaseWrapper wrapper) {
                                post.getAuthor().save();
                                post.getAuthor().getGeo().save();
                                post.getAuthor().getCounters().save();
                                post.getFlow().save();

                                StringBuilder hubAliases = new StringBuilder();
                                for (Hub hub : post.getHubs()) {
                                    hubAliases.append(hub.getAlias());
                                    hubAliases.append(';');
                                    hub.save();
                                }
                                post.setHubAliases(hubAliases.toString());
                                post.save();
                            }
                        }).add(post).build())
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

                                StringBuilder hubAliases = new StringBuilder();
                                for (Hub hub : post.getHubs()) {
                                    hubAliases.append(hub.getAlias());
                                    hubAliases.append(';');
                                    hub.save();
                                }
                                post.setHubAliases(hubAliases.toString());
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

    public int getNextPage(Response response) {
        return response.getNextPage() != null && response.getNextPage().getNext() > 0 ? response.getNextPage().getNext() : 1;
    }


    public interface AsyncData<T, F> {
        void onSuccess(T data, int nextPage);

        void onSuccessCache(F data, int nextPage);

        void onError();
    }
}
