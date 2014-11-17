package com.coursera.artem_grachyev.dailyselfie;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by artem_grachyev on 17.11.2014.
 */
public class SelfieView extends Activity {

    ImageView ivPhoto;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_layout);
    //    createDirectory();
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
    }

}
