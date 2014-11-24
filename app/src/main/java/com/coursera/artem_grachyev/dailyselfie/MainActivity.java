package com.coursera.artem_grachyev.dailyselfie;

import android.app.Activity;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends ListActivity {

    private SelfieViewAdapter mAdapter;
  //  Camera mCamera;
    ListView lv;

    File directory;
    String mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
  //  static final int REQUEST_TAKE_PHOTO = 1;
    final String TAG = "DailySelfie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        lv =  (ListView) findViewById(android.R.id.list);
        View footerView = getLayoutInflater().inflate(R.layout.selfie_list, null);
       // lv.addFooterView(footerView);
        mAdapter = new SelfieViewAdapter(getApplicationContext());
        setListAdapter(mAdapter);

      //  placesListView.addFooterView(R.layout.list);
    //    mAdapter = new ArrayAdapter<String>(this, R.layout.selfie_list);

    //    setListAdapter(mAdapter);
        //mAdapter = get
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.camera) {
           /* Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri());
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }*/
            dispatchTakePictureIntent();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
   //     releaseMediaRecorder();
   /*     if (mCamera != null)
            mCamera.release();
        mCamera = null;*/
    }


/*
    @Override
    protected void onResume()
    {
        if (null == mCamera) {
            try {

                // Returns first back-facing camera or null if no camera is
                // available.
                // May take a long time to complete
                // Consider moving this to an AsyncTask
                mCamera = Camera.open();

            } catch (RuntimeException e) {
                Log.e(TAG, "Failed to acquire camera");
            }

            // Ensure presence of camera or finish()
            if (null == mCamera)
                finish();
        }
    }
*/
    private Uri generateFileUri() {
        File file = null;
        file = new File(directory.getPath() + "/" + "Pictures/DailySelfie/"
            + System.currentTimeMillis() + ".jpg");

        Log.d(TAG, "fileName = " + file);
        return Uri.fromFile(file);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp;

        Log.d(TAG, timeStamp);
        Log.d(TAG, imageFileName);

        String storage = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + "/DailySelfie/" ;

        File storageDir = new File(storage);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
     //   Log.d(TAG, image.getAbsolutePath());
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d(TAG, ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivity(takePictureIntent);//, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    Log.d(TAG, "Intent is null");
                } else {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                  //  mSelfieView.setImageBitmap(imageBitmap);
                }
            }
        }
    }
*/

    protected void onStart(){
        super.onStart();
        Context context = getApplicationContext();
        Intent notificationIntent = new Intent(context, MainActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Resources res = context.getResources();


        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle("Daily Selfie")
                .setContentText("Time for another selfie")
                .setTicker("Time for another selfie").setWhen(System.currentTimeMillis()) // java.lang.System.currentTimeMillis()
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND).setAutoCancel(true)
                .setSmallIcon(android.R.drawable.ic_menu_camera);
        //        .setLargeIcon(BitmapFactory.decodeResource(res, R.id.camera));

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(101, builder.getNotification());
    }

}
