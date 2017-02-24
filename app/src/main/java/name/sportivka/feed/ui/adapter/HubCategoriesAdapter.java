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
import name.sportivka.feed.model.feed.HubCategory;

import static android.text.TextUtils.isEmpty;

/**
 * Created by daniil on 24.02.17.
 */


public class HubCategoriesAdapter extends RecyclerView.Adapter<HubCategoriesAdapter.BaseViewHolder> {


    private Context mContext;
    private FlowCursorList<HubCategory> mHubCategoryCacheList;
    private List<HubCategory> mHubCategoryList = new ArrayList<>();
    private boolean cacheMode = false;


    public HubCategoriesAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void cacheMode(boolean status) {
        cacheMode = status;
    }

    public void setHubCategoryList(FlowCursorList<HubCategory> hubCategoryCacheList) {
        this.cacheMode = true;
        this.mHubCategoryCacheList = hubCategoryCacheList;
    }

    public void setHubCategoryList(List<HubCategory> hubCategoryList) {
        this.cacheMode = false;
        this.mHubCategoryList.clear();
        for (HubCategory hubCategory : hubCategoryList) {
            mHubCategoryList.add(hubCategory);
        }
        notifyDataSetChanged();
    }

    public void addToHabCategoryList(List<HubCategory> hubCategoryList) {
        for (HubCategory hubCategory : hubCategoryList) {
            mHubCategoryList.add(hubCategory);
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

        return new ViewHolder(inflater.inflate(R.layout.hub_category_item_view, parent, false), mContext);


    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        HubCategory hubCategory = cacheMode ? mHubCategoryCacheList.getItem(position) : mHubCategoryList.get(position);
        if (hubCategory != null) {
            holder.bind(hubCategory);
        }
    }


    @Override
    public void onViewRecycled(BaseViewHolder holder) {
        super.onViewRecycled(holder);
        holder.clear();
    }

    @Override
    public int getItemCount() {
        return cacheMode ? mHubCategoryCacheList.getCount() : mHubCategoryList.size();
    }


    abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        BaseViewHolder(View view) {
            super(view);
        }

        abstract void clear();

        abstract void bind(HubCategory hubCategory);
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.hub_category_title_textview)
        TextView hubCategoryTitleTextview;
        @BindView(R.id.hub_category_count_textview)
        TextView hubCategoryCountTextview;

        Context context;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
        }


        @Override
        public void bind(HubCategory hubCategory) {
            if (hubCategory == null) return;
            hubCategoryTitleTextview.setText(isEmpty(hubCategory.getTitle()) ? "" : hubCategory.getTitle());
            hubCategoryCountTextview.setText(String.valueOf(hubCategory.getHubsCount()));
        }

        @Override
        void clear() {

        }


    }


}