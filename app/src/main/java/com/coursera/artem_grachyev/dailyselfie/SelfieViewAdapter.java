package com.coursera.artem_grachyev.dailyselfie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by artem_grachyev on 17.11.2014.
 */
public class SelfieViewAdapter extends BaseAdapter {

    private ArrayList<SelfieView> list = new ArrayList<SelfieView>();
    private static LayoutInflater inflater = null;
    //public ImageLoader imageLoader;
    private Context mContext;

    public SelfieViewAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // View newView = convertView;
        View newView = convertView;
        ItemView itemView;

        SelfieView sv = list.get(position);

        if (null == convertView){
            itemView = new ItemView();
            newView = inflater.inflate(R.layout.selfie_list, null);
            itemView.mSelfieView = (ImageView) newView.findViewById(R.id.ivPhoto);
            itemView.mDescriptionSelfieView = (TextView) newView.findViewById(R.id.timeStampSelfie);
            newView.setTag(itemView);
        } else {
            itemView = (ItemView) newView.getTag();
        }

        itemView.mSelfieView.setImageBitmap(sv.getBitmap());
        itemView.mDescriptionSelfieView.setText(sv.getName());

        return newView;

    }

    static class ItemView {

        private TextView mDescriptionSelfieView;
        private ImageView mSelfieView;

    }
}
