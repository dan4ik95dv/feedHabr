package name.sportivka.feed.provider;

import java.util.List;

import javax.inject.Inject;

import name.sportivka.feed.di.scope.AppScope;
import name.sportivka.feed.model.feed.Post;
import name.sportivka.feed.network.ConnectionDetector;
import name.sportivka.feed.network.api.PostApi;

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

    private void getNetworkSearchPosts(String search, int page, String sort, boolean getArticle, AsyncData<List<Post>> posts) {
    }

    private void getCacheSearchPosts(String search, int page, String sort, boolean getArticle, AsyncData<List<Post>> posts) {

    }

    private void getNetworkPubInteresting(int page, AsyncData<List<Post>> posts) {
    }

    private void getCachePubInteresting(int page, AsyncData<List<Post>> posts) {

    }

    private void getNetworkPubBest(String type, int page, AsyncData<List<Post>> posts) {

    }

    private void getCachePubBest(String type, int page, AsyncData<List<Post>> posts) {

    }

    private void getNetworkPubAll(int page, AsyncData<List<Post>> posts) {

    }

    private void getCachePubAll(int page, AsyncData<List<Post>> posts) {

    }

    private void getCachePost(long postId, boolean getArticle, AsyncData<Post> post) {

    }

    private void getNetworkPost(long postId, boolean getArticle, AsyncData<Post> post) {

    }

    void putPostsToCache(final List<Post> posts) {
        for (Post post : posts) {
            post.save();
        }
    }

    public interface AsyncData<T> {
        void onSuccess(T data, int nextPage, boolean isCache);

        void onError();
    }
}
