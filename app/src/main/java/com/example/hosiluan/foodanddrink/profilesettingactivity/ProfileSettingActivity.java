package com.example.hosiluan.foodanddrink.profilesettingactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Base64;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.hosiluan.foodanddrink.CircleImage;
import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.mainactivity.MainActivity;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static com.example.hosiluan.foodanddrink.loginactivity.LoginActivity.FB_USER_NAME;
import static com.example.hosiluan.foodanddrink.loginactivity.LoginActivity.LOGIN_TOKEN;
import static com.example.hosiluan.foodanddrink.loginactivity.LoginActivity.USER_EMAIL;
import static com.example.hosiluan.foodanddrink.loginactivity.LoginActivity.USER_GENDER;
import static com.example.hosiluan.foodanddrink.loginactivity.LoginActivity.USER_ID;
import static com.example.hosiluan.foodanddrink.loginactivity.LoginActivity.USER_PROFILE_PIC;

public class ProfileSettingActivity extends AppCompatActivity {

    private static final int TAKE_PHOTO_REQUEST_CODE = 1;
    private static final int PICK_PHOTO_REQUEST = 2;
    private CircleImage imgProfilePicture;
    private EditText edtUserName, edtUserEmail, edtUserGender;
    private Button btnSaveChange;
    private ImageButton imgBtnEdit, imgBtnBack;
    private PopupMenu popupChangeProfilePic;
    private Bitmap bitmapProfilePic;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
        setView();
        setUserInfo();
        inflatePopupMenuChangeProfilePic();
        setEvent();
    }

    public void setView() {
        imgProfilePicture = (CircleImage) findViewById(R.id.img_profile_picture_setting);
        edtUserEmail = (EditText) findViewById(R.id.edt_email);
        edtUserGender = (EditText) findViewById(R.id.edt_gender);
        edtUserName = (EditText) findViewById(R.id.edt_user_name);
        btnSaveChange = (Button) findViewById(R.id.btn_save_change);
        imgBtnEdit = (ImageButton) findViewById(R.id.img_btn_edit);
        popupChangeProfilePic = new PopupMenu(this, imgBtnEdit);
        imgBtnBack = (ImageButton) findViewById(R.id.img_btn_back_profile_setting);
    }

    public void inflatePopupMenuChangeProfilePic() {
        MenuInflater menuInflater = popupChangeProfilePic.getMenuInflater();
        menuInflater.inflate(R.menu.menu_change_profile_picture, popupChangeProfilePic.getMenu());
    }

    public void setUserInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(LOGIN_TOKEN, MODE_PRIVATE);
        String email = sharedPreferences.getString(USER_EMAIL, null);
        if (email != null) {
            edtUserEmail.setText(email);
        } else {
            edtUserEmail.setText("");
        }

        String gender = sharedPreferences.getString(USER_GENDER, null);

        if (gender != null) {
            edtUserGender.setText(sharedPreferences.getString(USER_GENDER, null));
        } else {
            edtUserGender.setText("");
        }
        edtUserName.setText(sharedPreferences.getString(FB_USER_NAME, null));
        Transformation transformation = new RoundedTransformationBuilder()
                .borderWidthDp(1)
                .cornerRadiusDp(5)
                .oval(true)
                .build();

        String encodedString = sharedPreferences.getString(USER_PROFILE_PIC, null);
        if (encodedString == null || encodedString.length() == 0) {
            String id = sharedPreferences.getString(USER_ID, null);
            Picasso.with(this)
                    .load("https://graph.facebook.com/" + id + "/picture?width=500&height=500")
                    .transform(transformation)
                    .into(imgProfilePicture);
        } else {
            Bitmap bitmap = decodeBitmapFromString(encodedString);
            imgProfilePicture.setImageBitmap(bitmap);
        }
    }


    public void setEvent() {

        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupChangeProfilePic.show();
            }
        });

        btnSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String encodedString = encodeBitmapToString(bitmapProfilePic);

                SharedPreferences sharedPreferences = getSharedPreferences(LOGIN_TOKEN, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(USER_EMAIL, edtUserEmail.getText().toString().trim());
                editor.putString(USER_GENDER, edtUserGender.getText().toString().trim());
                editor.putString(FB_USER_NAME, edtUserName.getText().toString().trim());

                if (encodedString.length() > 0) {
                    editor.putString(USER_PROFILE_PIC, encodedString);
                }
                editor.apply();
                finish();
            }
        });

        popupChangeProfilePic.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.take_photo: {
                        Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePhoto, TAKE_PHOTO_REQUEST_CODE);
                        break;
                    }
                    case R.id.choose_from_gallery: {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, PICK_PHOTO_REQUEST);
                        break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO_REQUEST_CODE: {
                if (resultCode == RESULT_OK) {
                    bitmapProfilePic = (Bitmap) data.getExtras().get("data");
                    imgProfilePicture.setImageBitmap(bitmapProfilePic);
                }
                break;
            }
            case PICK_PHOTO_REQUEST: {
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                        bitmapProfilePic = BitmapFactory.decodeStream(inputStream);
                        imgProfilePicture.setImageBitmap(bitmapProfilePic);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    public String encodeBitmapToString(Bitmap bitmap) {
        String encoded = "";
        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();

            encoded = Base64.encodeToString(b, Base64.DEFAULT);
        }
        return encoded;
    }

    public static Bitmap decodeBitmapFromString(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public void storeBitmapInSharePreferences(String input) {
        sharedPreferences = getSharedPreferences(LOGIN_TOKEN, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_PROFILE_PIC, input);
        editor.apply();
    }

}
