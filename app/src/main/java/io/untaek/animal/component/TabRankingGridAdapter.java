package io.untaek.animal.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import io.untaek.animal.firebase.PostInTimeline;

public class TabRankingGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<PostInTimeline> mdata;
    private TabRankingGridAdapter.ItemClickListener mClickListener;

    public TabRankingGridAdapter(Context c, List<PostInTimeline> data) {
        mContext = c;
        mdata = data;
    }

    @Override
    public int getCount() {
        return mdata.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        //imageView.setImageURI(Uri.parse(DataSet.myData.get(position).getImgResource()));
        //imageView.setImageBitmap(getImageBitmap(DataSet.myData.get(position).getImgResource()));

        imageView.setImageResource(mdata.get(position).getImgResource());
        return imageView;
    }

    public void setClickListener(TabRankingGridAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    // references to our images


}