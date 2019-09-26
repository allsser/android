package com.example.camera;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

public class MnistActivity extends AppCompatActivity {

    private MnistClassifier mnistClassifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mnist);

        ImageView photo = (ImageView)findViewById(R.id.photo);


        Intent i = getIntent();
        Uri photoUri = (Uri)i.getExtras().get("Uri");
        photo.setImageURI(null);
        photo.setImageURI(photoUri);
        onImageCaptured(photoUri);


        loadMnistClassifier();
    }


    private void loadMnistClassifier() {
        try {
            mnistClassifier = MnistClassifier.classifier(getAssets(), MnistModelConfig.MODEL_FILENAME);
            Log.i("TST00", mnistClassifier.toString());
        } catch (IOException e) {
            Toast.makeText(this, "MNIST model couldn't be loaded. Check logs for details.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void onImageCaptured(Uri picture) {
        ImageView ivFinalPreview = (ImageView)findViewById(R.id.ivFinalPreview);
        TextView tvClassification = (TextView)findViewById(R.id.tvClassification);


        //Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picture);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap squareBitmap = ThumbnailUtils.extractThumbnail(bitmap, getScreenWidth(), getScreenWidth());
//        ivPreview.setImageBitmap(squareBitmap);

        Bitmap preprocessedImage = ImageUtils.prepareImageForClassification(squareBitmap);
        ivFinalPreview.setImageBitmap(preprocessedImage);
        Log.i("TST0",preprocessedImage.toString());
        Log.i("TST1",mnistClassifier.recognizeImage(preprocessedImage).toString());

        List<Classification> recognitions = mnistClassifier.recognizeImage(preprocessedImage);
        tvClassification.setText(recognitions.toString());
        Log.i("TST",recognitions.toString());
    }

    private int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public void onClick(View view) {
        finish();
    }
}
