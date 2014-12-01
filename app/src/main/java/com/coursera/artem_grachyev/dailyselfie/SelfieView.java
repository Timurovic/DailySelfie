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

    private String mCurrentPhotoPath;
    private ImageView mImageView;
    private String mName;
    private String mSelfiePath;
    private Bitmap mBitmap;
    final String TAG = "DailySelfie";

    SelfieView(){

    }

    SelfieView(Bitmap bm, String name) {

        this.mBitmap = bm;
        this.mName = name;

    }

    public Bitmap getBitmap(){
        return mBitmap;
    }

    public void setBitmap(Bitmap mSelfieBitmap){
        this.mBitmap = mSelfieBitmap;
    }

    String getName() {
        return mName;
    }

    public void setName(String mSelfieName){
        this.mName = mSelfieName;
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }
}
