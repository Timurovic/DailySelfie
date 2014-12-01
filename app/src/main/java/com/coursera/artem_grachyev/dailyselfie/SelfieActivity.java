package com.coursera.artem_grachyev.dailyselfie;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by timur0vic on 01.12.14.
 */
public class SelfieActivity extends Activity {

    ImageView imageView;
    String mCurrentPhotoPath;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_layout);

        Intent intent = getIntent();
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("bitmap");
        mCurrentPhotoPath = (String) intent.getStringExtra("fileName");

        imageView = (ImageView) findViewById(R.id.ivPhoto);
        //bitmap.setHeight(56);
        imageView.setImageBitmap(bitmap);
    }

    private void setPic() {
        // Get the dimensions of the View
/*        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
*/
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        // Determine how much to scale down the image
        int scaleFactor = 20;

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }
}
