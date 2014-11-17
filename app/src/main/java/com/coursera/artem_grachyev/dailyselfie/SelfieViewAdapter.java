package com.coursera.artem_grachyev.dailyselfie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by artem_grachyev on 17.11.2014.
 */
public class SelfieViewAdapter extends BaseAdapter {

   // private ArrayList<PlaceRecord> list = new ArrayList<PlaceRecord>();
    private static LayoutInflater inflater = null;
    private Context mContext;

    public SelfieViewAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
