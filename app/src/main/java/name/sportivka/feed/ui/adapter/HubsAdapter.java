package name.sportivka.feed.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raizlabs.android.dbflow.list.FlowCursorList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import name.sportivka.feed.R;
import name.sportivka.feed.model.feed.Hub;

import static android.text.TextUtils.isEmpty;

/**
 * Created by daniil on 24.02.17.
 */


public class HubsAdapter extends RecyclerView.Adapter<HubsAdapter.BaseViewHolder> {


    private Context mContext;
    private FlowCursorList<Hub> mHubCacheList;
    private List<Hub> mHubList = new ArrayList<>();
    private boolean cacheMode = false;


    public HubsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void cacheMode(boolean status) {
        cacheMode = status;
    }

    public void setHubList(FlowCursorList<Hub> hubCacheList) {
        this.cacheMode = true;
        this.mHubCacheList = hubCacheList;
    }

    public void setHubList(List<Hub> hubList) {
        this.cacheMode = false;
        this.mHubList.clear();
        for (Hub hub : hubList) {
            mHubList.add(hub);
        }
        notifyDataSetChanged();
    }

    public void addToHabCategoryList(List<Hub> hubList) {
        for (Hub hub : hubList) {
            mHubList.add(hub);
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

        return new ViewHolder(inflater.inflate(R.layout.hub_item_view, parent, false), mContext);


    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Hub hub = cacheMode ? mHubCacheList.getItem(position) : mHubList.get(position);
        if (hub != null) {
            holder.bind(hub);
        }
    }


    @Override
    public void onViewRecycled(BaseViewHolder holder) {
        super.onViewRecycled(holder);
        holder.clear();
    }

    @Override
    public int getItemCount() {
        return cacheMode ? mHubCacheList.getCount() : mHubList.size();
    }

    public Hub getItem(int position) {
        return cacheMode ? mHubCacheList.getItem(position) : mHubList.get(position);
    }


    abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        BaseViewHolder(View view) {
            super(view);
        }

        abstract void clear();

        abstract void bind(Hub hub);
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.hub_category_title_textview)
        TextView hubTitleTextView;
        @BindView(R.id.hub_stat_textview)
        TextView hubStatTextView;
        Context context;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
        }


        @Override
        public void bind(Hub hub) {
            if (hub == null) return;
            hubTitleTextView.setText(isEmpty(hub.getTitle()) ? "" : hub.getTitle());
            StringBuilder statText = new StringBuilder();
            statText.append(context.getResources().getQuantityString(R.plurals.plurals_subscribers, hub.getCountSubscribers(), hub.getCountSubscribers()));
            statText.append(", ");
            statText.append(context.getResources().getQuantityString(R.plurals.plurals_posts, hub.getCountPosts(), hub.getCountPosts()));
            hubStatTextView.setText(statText);
        }

        @Override
        void clear() {

        }


    }


}