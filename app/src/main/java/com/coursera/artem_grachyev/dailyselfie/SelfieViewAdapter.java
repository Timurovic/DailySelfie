package com.coursera.artem_grachyev.dailyselfie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
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
            newView = inflater.inflate(R.layout.selfie_list, parent, false);
            itemView.mSelfieView = (ImageView) newView.findViewById(R.id.imageView);
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

        ImageView mSelfieView;
        TextView mDescriptionSelfieView;

    }

    public void add(SelfieView sv){
        list.add(sv);
        notifyDataSetChanged();
    }

    public ArrayList<SelfieView> getList(){
        return list;
    }

    public void removeAllViews(){
        list.clear();
        this.notifyDataSetChanged();
    }

    public void addAllViews(){
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + "/DailySelfie/");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File[] files = storageDir.listFiles();
        for (File f : files)
        {
            Bitmap bitmap = setPic(f.getAbsolutePath());
            SelfieView selfieView = new SelfieView();
            selfieView.setBitmap(bitmap);
            selfieView.setName(f.getName());

            Log.d("DailySelfie", "selfieView " + selfieView);
            add(selfieView);
        }
    }

    private Bitmap setPic(String path) {

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        int scaleFactor = 5;

        // Decode the image  file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);

        Log.d("DailySelfie", "bitmap " + bitmap);
        return bitmap;
    }
}
