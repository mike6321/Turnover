package com.example.joguk.criminalintent;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.UUID;

public class FullImageActivity extends AppCompatActivity {
    // static variable
    private final static String EXTRA_FILE_ID = "com.example.joguk.criminalintent.file";

    // member variable
    private ImageView mPhotoView;
    private static File mPhotoFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPhotoFile = (File) getIntent().getSerializableExtra(EXTRA_FILE_ID);

        mPhotoView = (ImageView) findViewById(R.id.full_image_view);
        updatePhotoView();
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), this);
            mPhotoView.setImageBitmap(bitmap);
        }
    }

    public static Intent newInstance(Context packageContext, String photoFileName) {
        Intent intent = new Intent(packageContext, FullImageActivity.class);
        intent.putExtra(EXTRA_FILE_ID, mPhotoFile);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean b = super.onCreateOptionsMenu(menu);
        if(b) {
            getMenuInflater().inflate(R.menu.activity_fullimage, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.paste_image:
                getClipboardImage();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getClipboardImage(){
//        ClipboardManager clipboard = (ClipboardManager) getSystemService(ACTIVITY_SERVICE);
//        ContentResolver cr = getContentResolver();
//        ClipData clip = clipboard.getPrimaryClip();

//        if(clip != null) {
//            ClipData.Item item = clip.getItemAt(0);
//            Uri pasteUri = item.getUri();
//            if (pasteUri != null) {
//                // Is this a content URI?
//               String uriMimeType = cr.getType(pasteUri);
//               if(uriMimeType != null){
//                   if(uriMimeType.equals("Image/jpg")){
//
//                   }
//               }
//            }
//        }
    }
}
