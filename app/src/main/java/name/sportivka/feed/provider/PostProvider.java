package name.sportivka.feed.provider;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.list.FlowQueryList;
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

    public void getPost(long postId, boolean getArticle, AsyncData<Post> post) {
        if (connectionDetector.isConnectingToInternet())
            getNetworkPost(postId, getArticle, post);
        else
            getCachePost(postId, getArticle, post);
    }

    public void getPubAll(int page, AsyncData<List<Post>> posts) {
        if (connectionDetector.isConnectingToInternet())
            getNetworkPubAll(page, posts);
        else
            getCachePubAll(page, posts);
    }


    public void getPubBest(String type, int page, AsyncData<List<Post>> posts) {
        if (connectionDetector.isConnectingToInternet())
            getNetworkPubBest(type, page, posts);
        else
            getCachePubBest(type, page, posts);
    }

    public void getPubInteresting(int page, AsyncData<List<Post>> posts) {
        if (connectionDetector.isConnectingToInternet())
            getNetworkPubInteresting(page, posts);
        else
            getCachePubInteresting(page, posts);
    }

    public void searchPosts(String search, int page, String sort, boolean getArticle, AsyncData<List<Post>> posts) {
        if (connectionDetector.isConnectingToInternet())
            getNetworkSearchPosts(search, page, sort, getArticle, posts);
        else
            getCacheSearchPosts(search, page, sort, getArticle, posts);
    }

    private void getNetworkSearchPosts(String search, int page, String sort, boolean getArticle, final AsyncData<List<Post>> asyncData) {
        postApi.searchPosts(search, page, sort, getArticle).enqueue(new Callback<Response<List<Post>>>() {
            @Override
            public void onResponse(Call<Response<List<Post>>> call, retrofit2.Response<Response<List<Post>>> response) {
                if (response.isSuccessful()) {
                    putPostsToCache(response.body().getData());
                    asyncData.onSuccess(response.body().getData(), 0, false);
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

    private void getCacheSearchPosts(String search, int page, String sort, boolean getArticle, AsyncData<List<Post>> asyncData) {
        ConditionGroup conditionGroup = ConditionGroup.clause()
                .or(Post_Table.title.like("%" + search + "%"))
                .or(Post_Table.previewHtml.like("%" + search + "%"))
                .or(Post_Table.tagsString.like("%" + search + "%"))
                .or(Post_Table.textHtml.like("%" + search + "%"));

        FlowQueryList<Post> result = SQLite.select().from(Post.class).where(conditionGroup).flowQueryList();
        int nextPage = result.getCount() == Constants.PER_PAGE ? page + 1 : 0;
        asyncData.onSuccess(result, nextPage, true);
    }

    private void getNetworkPubInteresting(int page, final AsyncData<List<Post>> asyncData) {
        postApi.getPubInteresting(page, Constants.INCLUDE, Constants.EXCLUDE_WITHOUT_FLOW, Constants.PER_PAGE).enqueue(new Callback<Response<List<Post>>>() {
            @Override
            public void onResponse(Call<Response<List<Post>>> call, retrofit2.Response<Response<List<Post>>> response) {
                if (response.isSuccessful()) {
                    putPostsToCache(response.body().getData());
                    asyncData.onSuccess(response.body().getData(), 0, false);
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

    private void getCachePubInteresting(int page, AsyncData<List<Post>> asyncData) {
        FlowQueryList<Post> result = SQLite.select().from(Post.class).where(Post_Table.interesting.eq(true)).flowQueryList();
        int nextPage = result.getCount() == Constants.PER_PAGE ? page + 1 : 0;
        asyncData.onSuccess(result, nextPage, true);
    }

    private void getNetworkPubBest(String type, int page, final AsyncData<List<Post>> asyncData) {
        postApi.getPubBest(type, page, Constants.INCLUDE, Constants.EXCLUDE_WITHOUT_FLOW, Constants.PER_PAGE).enqueue(new Callback<Response<List<Post>>>() {
            @Override
            public void onResponse(Call<Response<List<Post>>> call, retrofit2.Response<Response<List<Post>>> response) {
                if (response.isSuccessful()) {
                    putPostsToCache(response.body().getData());
                    asyncData.onSuccess(response.body().getData(), 0, false);
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

    private void getCachePubBest(String type, int page, AsyncData<List<Post>> asyncData) {
        FlowQueryList<Post> result = SQLite.select().from(Post.class).orderBy(Post_Table.score, false).flowQueryList();
        int nextPage = result.getCount() == Constants.PER_PAGE ? page + 1 : 0;
        asyncData.onSuccess(result, nextPage, true);
    }

    private void getNetworkPubAll(int page, final AsyncData<List<Post>> asyncData) {
        postApi.getPubAll(page, Constants.INCLUDE, Constants.EXCLUDE_WITHOUT_FLOW, Constants.PER_PAGE).enqueue(new Callback<Response<List<Post>>>() {
            @Override
            public void onResponse(Call<Response<List<Post>>> call, retrofit2.Response<Response<List<Post>>> response) {
                if (response.isSuccessful()) {
                    putPostsToCache(response.body().getData());
                    asyncData.onSuccess(response.body().getData(), 0, false);
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

    private void getCachePubAll(int page, AsyncData<List<Post>> asyncData) {
        FlowQueryList<Post> result = SQLite.select().from(Post.class).flowQueryList();
        int nextPage = result.getCount() == Constants.PER_PAGE ? page + 1 : 0;
        asyncData.onSuccess(result, nextPage, true);
    }


    private void getNetworkPost(long postId, boolean getArticle, final AsyncData<Post> asyncData) {
        postApi.getPost(postId, getArticle).enqueue(new Callback<Response<Post>>() {
            @Override
            public void onResponse(Call<Response<Post>> call, retrofit2.Response<Response<Post>> response) {
                if (response.isSuccessful()) {
                    putPostToCache(response.body().getData());
                    asyncData.onSuccess(response.body().getData(), 0, false);
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

    private void getCachePost(long postId, boolean getArticle, AsyncData<Post> asyncData) {
        Post result = SQLite.select().from(Post.class).where(Post_Table.myId.eq(postId)).querySingle();
        asyncData.onSuccess(result, 0, true);
    }

    void putPostToCache(final Post post) {
        FlowManager.getDatabase(MyDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<Post>() {
                            @Override
                            public void processModel(Post post, DatabaseWrapper wrapper) {
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
