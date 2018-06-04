package com.example.rahulsharma.imagerotate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    FloatingActionButton add, camera, gallery;
    Button button;
    ImageView imageView, backbt;
    Uri mUri;
    int wid, hei;
    Bitmap obitmap, resbitmap, bitmap;
    boolean isOpen = false;
    static int flag = 0;
    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 1889;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gallery = (FloatingActionButton) findViewById(R.id.gallery);
        imageView = (ImageView) findViewById(R.id.imageView);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            camera.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST);
            }
        });




    }


    Bitmap useBitmap(Uri uri)
    {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == CAMERA_REQUEST) {

                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                imageView.setImageBitmap(bmp);
            } else if (requestCode == GALLERY_REQUEST) {

                Uri selectedImageUri = data.getData();
                mUri = selectedImageUri;
                try {
                    resbitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                wid = resbitmap.getWidth();
                hei = resbitmap.getHeight();
                imageView.requestLayout();
                imageView.getLayoutParams().height = hei;

                // Apply the new width for ImageView programmatically
                imageView.getLayoutParams().width = wid;

                // Set the scale type for ImageView image scaling
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageBitmap(resbitmap);
            }

        }
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//        outState.putParcelable("BitmapImage", obitmap);
//        super.onSaveInstanceState(outState);
//
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        resbitmap = savedInstanceState.getParcelable("BitmapImage");
//        this.obitmap = resbitmap;
//        imageView.setImageBitmap(obitmap);
//        super.onRestoreInstanceState(savedInstanceState);
//    }
}