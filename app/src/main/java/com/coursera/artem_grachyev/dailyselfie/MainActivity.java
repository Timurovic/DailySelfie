package com.coursera.artem_grachyev.dailyselfie;

import android.app.Activity;
import android.app.AlarmManager;
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
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ListActivity {

    private SelfieViewAdapter mAdapter;
    //  Camera mCamera;
    //  ListView lv;
    File directory;
    String mCurrentPhotoPath, imageFileName, mFileName;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final long INITIAL_ALARM_DELAY_TWO_MINUTES = 2 * 60 * 1000L;

    //  static final int REQUEST_TAKE_PHOTO = 1;
    final String TAG = "DailySelfie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    setContentView(R.layout.main);



        ListView lv = getListView();

        View footerView = getLayoutInflater().inflate(R.layout.main, null, false);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SelfieView selfieView = (SelfieView) mAdapter.getItem(position);
                Bitmap bitmap = selfieView.getBitmap();
                String fileName = selfieView.getName();

                Intent intentSelfie = new Intent(MainActivity.this, SelfieActivity.class);
                intentSelfie.putExtra("bitmap", bitmap);
                intentSelfie.putExtra("fileName", fileName);

                startActivity(intentSelfie);
            }
            
        });

        lv.addFooterView(footerView);
        mAdapter = new SelfieViewAdapter(getApplicationContext());
        mAdapter.addAllViews();
        setListAdapter(mAdapter);

        setAlarm();
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


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date().getTime());
        imageFileName = timeStamp;

        String storage = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + "/DailySelfie/" ;

        File storageDir = new File(storage);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        /*
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        */

        File image = new File(storageDir.getAbsolutePath() + File.separator + imageFileName + ".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        mFileName = image.getAbsolutePath();
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
                Log.d(TAG, "IOException");
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            galleryAddPic();
            Bitmap imageBitmapT = setPic();

            SelfieView selfieView = new SelfieView();
            selfieView.setName(imageFileName);

            selfieView.setBitmap(imageBitmapT);

            mAdapter.add(selfieView);

            Log.i(TAG, "exit onActivityResult");
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private Bitmap setPic(){



        int scaleFactor = 5;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mFileName, bmOptions);

        return bitmap;
    }

    private void setAlarm() {
        Context context = getApplicationContext();
        Intent intent = new Intent(context, AlarmReceiver.class);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // calendar.add(Calendar.SECOND, 10);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 1, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis() + INITIAL_ALARM_DELAY_TWO_MINUTES, INITIAL_ALARM_DELAY_TWO_MINUTES, pendingIntent);

    }

}
