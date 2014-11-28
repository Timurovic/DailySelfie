package com.coursera.artem_grachyev.dailyselfie;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by artem_grachyev on 17.11.2014.
 */
public class SelfieView extends Activity {

    ImageView ivPhoto;
    static final int REQUEST_TAKE_PHOTO = 1;
    ImageView mImageView;
    String mText, mName;
    Bitmap mBitmap;
    final String TAG = "DailySelfie";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_layout);
    //    createDirectory();
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        String path = Environment.getExternalStorageDirectory().toString()+"/Pictures";
        Log.d("Files", "Path: " + path);
    }

    SelfieView(Bitmap bm, String text, String name) {

        this.mBitmap = bm;
        this.mText = text;
        this.mName = name;

    }

    public ImageView getImageView()
    {
        return mImageView;
    }


    public Bitmap getBitmap(){
        return mBitmap;
    }

    String getText() {
        return mText;
    }

    String getName() {
        return mName;
    }
}
