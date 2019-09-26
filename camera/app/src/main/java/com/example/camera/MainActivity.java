package com.example.camera;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgMain;
    private Button btnCamera, btnAlbum;

    private static final int PICK_FROM_CAMERA = 1;  // 카메라 촬영으로 사진 가져오기
    private static final int PICK_FROM_ALBUM = 2;   // 앨범에서 사진 가져오기
    private static final int CROP_FROM_CAMERA = 3;  // 가져온 사진을 자르기 위한 변수

    private Uri photoUri;
    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA};  // 권한 설정 변수
    private static final int MULTIPLE_PERMISSIONS = 101;      // 권한 동의 여부 문의 후 CallBack 함수에 쓰일 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();  // 권한 묻기
        initView();
    }

    private boolean checkPermissions() {
        int result;
        List<String> permissionList = new ArrayList<>();
        for(String pm : permissions) {
            result = ContextCompat.checkSelfPermission(this, pm);
            if(result != PackageManager.PERMISSION_GRANTED) {  // 사용자가 해당 권한을 가지고 있지 않을 경우 리스트에 해당 권한명 추가
                permissionList.add(pm);
            }
        }
        if(!permissionList.isEmpty()) {  // 권한이 추가되었으면 해당 리스트가 empty가 아니므로 request 즉 권한을 요청합니다.
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return  true;
    }


    private void initView() {
        imgMain = findViewById(R.id.imageView);
        btnCamera = findViewById(R.id.btn_camera);
        btnAlbum = findViewById(R.id.btn_album);

        btnCamera.setOnClickListener(this);
        btnAlbum.setOnClickListener(this);
    }

    // Camera로 사진 찍기
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 사진을 찍기 위하여 설정합니다.
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "이미지 처리 오류! 다시 시도해주세요.",
                    Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        if(photoFile != null) {
            photoUri = FileProvider.getUriForFile(MainActivity.this,
                    "com.example.camera.provider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);  // 사진을 찍어 해당 Content uri를 photoUri에 적용시키기 위함
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }

    // Android M에서는 Uri.fromFile 함수를 사용했으나 7.0부터는 이 함수를 사용할 시 FileUriExposedException이
    // 발생하므로 아래와 같이 함수를 작성.
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "IP" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/test/"); //test라는 경로에 이미지를 저장하기 위함
        if(!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
    }

    // 앨범에서 이미지 선택
    private void goToAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);  // ACTION_PICK 즉 사진을 고르겠다
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera:
                takePhoto();
                break;
            case R.id.btn_album:
                goToAlbum();
                break;
        }
    }

    // 아래는 권한 요청 Callback 함수입니다. PERMISSION_GRANTED로 권한을 획득했는지 확인할 수 있습니다. 아래에서는 != 를 사용했기에
    // 권한 사용에 동의를 안했을 경우 if문으로 코딩되었습니다.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if(grantResults.length > 0) {
                    for(int i = 0; i<permissions.length; i++) {
                        if(permissions[i].equals(this.permissions[0])) {
                            if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();
                            }
                        } else if(permissions[i].equals(this.permissions[1])) {
                            if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();
                            }
                        } else if (permissions[i].equals(this.permissions[2])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();
                            }
                        }
                    }
                } else {
                    showNoPermissionToastAndFinish();
                }
                return;
            }
        }
    }

    // 권한 획득에 동의를 하지 않았을 경우 아래 Toast 메세지를 띄우며 해당 Activity를 종료시킵니다.
    private void showNoPermissionToastAndFinish() {
        Toast.makeText(this, "권한 요청에 동의 해주셔야 이용 가능합니다. 설정에서 권한 허용 하시기 바랍니다.",
                Toast.LENGTH_SHORT).show();
        finish();
    }

    // onActivityResult 콜백 함수 및 가장 중요한 Crop자르기 함수 부분
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode != RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다0.", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == PICK_FROM_ALBUM) {
            if(data == null) {
                Log.i("Test",data.toString());
                return;
            }
            photoUri = data.getData();
            Log.i("TestTEST",data.getData().toString());
            cropImage();
        } else if (requestCode == PICK_FROM_CAMERA) {
            cropImage();
            // 갤러리에 나타나게
            MediaScannerConnection.scanFile(MainActivity.this,  // 앨범에 사진을 보여주기 위해 Scan을 한다.
                    new String[]{photoUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {

                        }
                    });
        } else if(requestCode == CROP_FROM_CAMERA) {
//            imgMain.setImageURI(null);
//            imgMain.setImageURI(photoUri);
//            revokeUriPermission(photoUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Intent i = new Intent();
            i.setAction("MNIST");
            revokeUriPermission(photoUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            i.putExtra("Uri", photoUri);
            startActivity(i);
        }
    }

    private void cropImage() {
        this.grantUriPermission("com.android.camera", photoUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");

        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
        grantUriPermission(list.get(0).activityInfo.packageName, photoUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        int size = list.size();
        if (size == 0) {
            Toast.makeText(this, "취소 되었습니다2.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(this, "용량이 큰 사진의 경우 시간이 오래 걸릴 수 있습니다.", Toast.LENGTH_SHORT).show();
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 3);
            intent.putExtra("aspectY", 4);
            intent.putExtra("scale", true);
            File croppedFileName = null;
            try {
                croppedFileName = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            File folder = new File(Environment.getExternalStorageDirectory() + "/test/");
            File tempFile = new File(folder.toString(), croppedFileName.getName());

            photoUri = FileProvider.getUriForFile(this,
                    "com.example.camera.provider", tempFile);

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);


            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString()); //Bitmap 형태로 받기 위해 해당 작업 진행

            Intent i = new Intent(intent);
            ResolveInfo res = list.get(0);
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            grantUriPermission(res.activityInfo.packageName, photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            startActivityForResult(i, CROP_FROM_CAMERA);


        }

    }
}
