package com.example.dapp;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Test extends AppCompatActivity {
    private String mExtStorDir;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int REQUEST_PERMISSION=7;
    private static final String CROP_IMAGE_FILE_NAME = "cropPhoto.jpg";
    private Uri mUriPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        Button button=findViewById(R.id.ceshi_button);
        ImageView imageView=findViewById(R.id.ceshi_image);
//        mExtStorDir= Environment.getExternalStorageDirectory()
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
