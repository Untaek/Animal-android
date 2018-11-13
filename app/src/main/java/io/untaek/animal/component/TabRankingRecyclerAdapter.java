package io.untaek.animal.component;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import io.untaek.animal.R;
import io.untaek.animal.PostDetailActivity;
import io.untaek.animal.firebase.PostInTimeline;

public class TabRankingRecyclerAdapter extends RecyclerView.Adapter<TabRankingRecyclerAdapter.ViewHolder> {

    private List<PostInTimeline> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;

    // data is passed into the constructor
    public TabRankingRecyclerAdapter(Context context, List<PostInTimeline> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.component_tab_rank_recycleitem, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PostInTimeline data = mData.get(position);

        holder.recyclerViewImage.setImageResource(data.getImgResource());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView recyclerViewImage;

        ViewHolder(View itemView) {
            super(itemView);
            recyclerViewImage = itemView.findViewById(R.id.tab_rank_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PostDetailActivity.class);
                    intent.putExtra("data", mData.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }


    }

    // convenience method for getting data at click position
    public PostInTimeline getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}