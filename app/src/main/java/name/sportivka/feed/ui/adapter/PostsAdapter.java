package name.sportivka.feed.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raizlabs.android.dbflow.list.FlowCursorList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import name.sportivka.feed.R;
import name.sportivka.feed.model.feed.Post;

import static android.text.TextUtils.isEmpty;

/**
 * Created by daniil on 24.02.17.
 */


public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.BaseViewHolder> {


    private Context mContext;
    private List<Post> mPostList = new ArrayList<>();
    private FlowCursorList<Post> mPostCacheList;
    private boolean cacheMode = false;


    public PostsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void cacheMode(boolean status) {
        cacheMode = status;
    }

    public void setPostList(FlowCursorList<Post> postCacheList) {
        this.cacheMode = true;
        this.mPostCacheList = postCacheList;
        notifyDataSetChanged();
    }

    public void setPostList(List<Post> postList) {
        this.cacheMode = false;
        this.mPostList.clear();
        for (Post post : postList) {
            mPostList.add(post);
        }
        notifyDataSetChanged();
    }

    public void addPostList(List<Post> postList) {
        this.cacheMode = false;
        for (Post post : postList) {
            mPostList.add(post);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.post_card_item_view, parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Post post = cacheMode ? mPostCacheList.getItem(position) : mPostList.get(position);
        if (post != null) {
            holder.bind(post);
        }
    }


    @Override
    public void onViewRecycled(BaseViewHolder holder) {
        super.onViewRecycled(holder);
        holder.clear();
    }

    @Override
    public int getItemCount() {
        return cacheMode ? mPostCacheList.getCount() : mPostList.size();
    }

    public Post getItem(int position) {
        return cacheMode ? mPostCacheList.getItem(position) : mPostList.get(position);
    }

    abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        BaseViewHolder(View view) {
            super(view);
        }

        abstract void clear();

        abstract void bind(Post post);
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.date_created_post_textview)
        TextView dateCreatedPostTextView;
        @BindView(R.id.author_post_textview)
        TextView authorPostTextView;
        @BindView(R.id.title_post_textview)
        TextView titlePostTextView;
        @BindView(R.id.hub_title_post_textview)
        TextView hubTitlePostTextView;
        @BindView(R.id.score_post_textview)
        TextView scorePostTextView;
        @BindView(R.id.views_post_textview)
        TextView viewsPostTextView;
        @BindView(R.id.comments_post_textview)
        TextView commentsPostTextView;

        Context context;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
        }


        @Override
        public void bind(Post post) {
            if (post == null) return;
            SimpleDateFormat fmt = new SimpleDateFormat("d MMMM yyyy HH:mm");

            dateCreatedPostTextView.setText(fmt.format(post.getTimePublished()));
            authorPostTextView.setText(post.getAuthor() != null ? post.getAuthor().getLogin() : "");
            titlePostTextView.setText(isEmpty(post.getTitle()) ? "" : post.getTitle());
            hubTitlePostTextView.setText(isEmpty(post.getTagsString()) ? "" : post.getTagsString());
            scorePostTextView.setText(String.valueOf(post.getScore()));
            viewsPostTextView.setText(String.valueOf(post.getReadingCount()));
            commentsPostTextView.setText(String.valueOf(post.getCommentsCount()));
        }

        @Override
        void clear() {

        }


    }


}